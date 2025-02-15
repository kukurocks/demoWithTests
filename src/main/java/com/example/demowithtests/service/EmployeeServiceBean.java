package com.example.demowithtests.service;

import com.example.demowithtests.domain.*;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.util.annotation.Profiler;
import com.example.demowithtests.util.annotation.entity.ActivateCustomAnnotations;
import com.example.demowithtests.util.annotation.entity.Name;
import com.example.demowithtests.util.annotation.entity.ToLowerCase;
import com.example.demowithtests.util.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Profiler
@RequiredArgsConstructor
@Slf4j
@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PassportService passportService;
    private final WorkPlaceService workPlaceService;
    private final EmployeeWorkPlaceService employeeWorkPlaceService;
    private final ApplicationEventPublisher eventPublisher;


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Employee addWorkPlace(Integer empId, Integer wpId) {
        Employee employee = employeeRepository.findById(empId).orElseThrow(ResourceNotFoundException::new);
        WorkPlace workPlace = workPlaceService.getWorkPlace(wpId);
        if (workPlace.getHasFreePlaces().equals(Boolean.FALSE))
            throw new WorkPlaceIsHandedException("Work Place with id:" + wpId + " do not have vacancy place");
        if (employeeWorkPlaceService.getCountHeldWorkPlacesOfEmployee(empId) == 3) {
            throw new WorkPlaceIsHandedException("Employee already has 3 held work places");
        }
        employeeWorkPlaceService.addConnection(employee, workPlace);
        int freeWorkPlaces = workPlace.getQuantity() - employeeWorkPlaceService.getCountActiveBusyPlaces(wpId);
        workPlace.setFreePlacesCount(freeWorkPlaces);
        if (freeWorkPlaces == 0)
            workPlace.setHasFreePlaces(Boolean.FALSE);

        workPlaceService.addWorkPlace(workPlace);
        return getById(empId);
    }

    @Override
    public Employee cancelPassport(Integer empId) {

        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(ResourceNotFoundException::new);
        Passport passport = Optional.ofNullable(employee.getPassport())
                .orElseThrow(ResourceNotFoundException::new);
        passportService.cancel(passport);
        employee.setPassport(null);
        Employee savedEmployee = employeeRepository.save(employee);
        eventPublisher.publishEvent(new PassportEvent(Action.CANCEL, empId));

        return savedEmployee;
    }

    @Override
    public Employee handPassport(Integer employeeId, Integer passportId) throws PassportIsHandedException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        if (employee.getPassport() != null) {
            throw new PassportIsHandedException("Employee must have only one passport");
        }
        employee.setPassport(passportService.handOver(passportId));

        Employee savedEmployee = employeeRepository.save(employee);
        eventPublisher.publishEvent(new PassportEvent(Action.HANDOVER, savedEmployee.getId()));

        return savedEmployee;
    }

    @Override
    @ActivateCustomAnnotations({Name.class, ToLowerCase.class})
    // @Transactional(propagation = Propagation.MANDATORY)
    public Employee create(Employee employee) {
        Set<Address> addresses = employee.getAddresses();
        addresses.stream().peek(a -> a.setCountry(employee.getCountry())
        ).collect(Collectors.toSet());
        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            throw new DataIntegrityViolationException("Country cannot be null");
        }
    }

    @Transactional
    @Override
    public Employee updateEM(Integer updatedEmployeeId) {

        Employee employee = entityManager.find(Employee.class, updatedEmployeeId);
        Employee oldEmployee = new Employee();
        oldEmployee.setName(employee.getName());

        entityManager.detach(employee);
        employee.setName("New Name");

        if (employee.getName().length() > oldEmployee.getName().length()) {

            return entityManager.merge(employee);
        } else {
            return employee;
        }
    }

    @Transactional
    @Override
    public Employee updateBeforeLongTermOperation(Integer updatedEmployeeId) throws InterruptedException {
        Employee employee = entityManager.find(Employee.class, updatedEmployeeId);

        employee.setName("QuickName");
        entityManager.flush();
        entityManager.detach(employee);

        Thread.sleep(30 * 1000);

        employee.setEmail("longterm@gmail.com");

        return entityManager.merge(employee);
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> list = employeeRepository.findAll();
        list = list.stream()
                .filter(e -> !e.getDeleted())
                .collect(Collectors.toList());
        if (list.isEmpty()) {
            throw new ListEmptyException();
        }
        return list;
    }

    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Employee getById(Integer id) {
        final var employee = employeeRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (employee.getDeleted()) throw new ResourceWasDeletedException();
        return employee;
    }

    @Override
    @Transactional
    public Employee updateById(Integer id, Employee employee) {
        return employeeRepository.findById(id)
                .map(entity -> {
                    if (isFieldNew(entity.getName(), employee.getName()))
                        entity.setName(employee.getName());
                    if (isFieldNew(entity.getEmail(), employee.getEmail()))
                        entity.setEmail(employee.getEmail());
                    if (isFieldNew(entity.getGender(), employee.getGender()))
                        entity.setGender(employee.getGender());
                    if (isFieldNew(entity.getCountry(), employee.getCountry()))
                        entity.setCountry(employee.getCountry());
                    if (!employee.getAddresses().isEmpty()) {

                        for (Address a : employee.getAddresses()) {
                            Address addresses = entity.getAddresses().iterator().next();
                            if (a.getCity() == null)
                                a.setCity(addresses.getCity());
                            if (a.getCountry() == null)
                                a.setCountry(addresses.getCountry());
                            if (a.getStreet() == null)
                                a.setStreet(addresses.getStreet());
                        }
                    }
                    entity.getAddresses().forEach(address -> address.setAddressHasActive(Boolean.FALSE));
                    entity.setAddresses(Stream
                            .concat(entity.getAddresses().stream(),
                                    employee.getAddresses().stream())
                            .collect(Collectors.toSet()));

                    return employeeRepository.save(entity);
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Page<Employee> getActiveAddressesByCountry(String country, Pageable pageable) {
        return employeeRepository.findAllWhereIsActiveAddressByCountry(country, pageable);
    }

    @Override
    public List<String[]> findEmployeeWithThemEmailLength(String employee, String sortBY) {
        return employeeRepository.findByAsArrayAndSort(employee, Sort.by(sortBY));
    }

    @Override
    public List<Employee> getByName(String name) {
        return null;
    }

    @Override
    public void removeById(Integer id) {

        Employee employee = employeeRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (employee.getDeleted()) {
            throw new ResourceWasDeletedException();
        }
        employee.setDeleted(true);
        employeeRepository.save(employee);


    }

    @Override
    public void removeAll() {
        List<Employee> list = employeeRepository.findAll();
        if (!list.isEmpty()) {
            for (Employee e : list) {
                e.setDeleted(true);
                employeeRepository.save(e);
            }
        } else {
            throw new ListEmptyException();
        }
    }

    // Oleg's methods \\
    @Override
    public Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder) {
        // create Pageable object using the page, size and sort details
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        // fetch the page object by additionally passing pageable with the filters
        return employeeRepository.findByCountryContaining(country, pageable);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    @Override
    public List<String> getAllEmployeeCountry() {
        log.info("getAllEmployeeCountry() - start:");
        List<Employee> employeeList = employeeRepository.findAll();
        List<String> countries = employeeList.stream()
                .map(Employee::getCountry)
                .collect(Collectors.toList());
        /*List<String> countries = employeeList.stream()
                .map(Employee::getCountry)
                //.sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());*/

        log.info("getAllEmployeeCountry() - end: countries = {}", countries);
        return countries;
    }

    @Override
    public List<String> getSortCountry() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()
                .map(Employee::getCountry)
                .filter(c -> c.startsWith("U"))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> findEmails() {
        var employeeList = employeeRepository.findAll();

        var emails = employeeList.stream()
                .map(Employee::getEmail)
                .toList();

        var opt = emails.stream()
                .filter(s -> s.endsWith(".com"))
                .findFirst()
                .orElse("error?");
        return Optional.of(opt);
    }

    // My methods \\
    @Override
    public List<Employee> filterByCountry(String country) {
        return employeeRepository.findByCountry(country);
    }

    @Override
    public List<Employee> findAllByEmailIsNull() {

        return employeeRepository.findAllByEmailIsNull()
                .stream()
                .filter(e -> !e.getDeleted())
                .collect(Collectors.toList());

    }

    @Override
    public List<Employee> findAllWithSyntaxError() {

        List<Employee> allEmp = employeeRepository.findAll();
        List<Employee> resultList = new ArrayList<>();

        for (Employee e : allEmp) {
            if (!e.getDeleted() || e.getCountry() != null) {
                String country = e.getCountry();
                if (isLowerCase(country)) {
                    e.setCountry(capitalizeString(country));
                }
                Set<Address> addresses = e.getAddresses();
                if (!addresses.isEmpty()) {
                    for (Address address : addresses) {
                        String country1 = address.getCountry();
                        if (country1 != null) {
                            if (isLowerCase(country1)) {
                                address.setCountry(capitalizeString(country1));
                            }
                        }
                    }
                }

                resultList.add(e);
                employeeRepository.save(e);

            }
        }
        return resultList;
    }

    @Override
    public Employee findEmployeeByEmail(String email) {

        return employeeRepository.findEmployeeByEmail(email)
                .orElseThrow(ResourceNotFoundException::new);
    }

    // technical methods \\

    private boolean isFieldNew(Object existField, Object newField) {
        return newField != null && !newField.equals(existField);
    }

    public boolean isLowerCase(String str) {
        return str.equals(str.toLowerCase());
    }

    public String capitalizeString(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }


    /* public boolean isValid(Employee employee) {
        String regex = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(employee.getPhone());
        boolean isFound = matcher.find();
        if (isFound) {
            System.out.println("Number is valid");
            return true;
        } else {
            System.out.println("Number is invalid");
            return false;
        }
    }

    public boolean isVodafone(Employee employee) {
        String regex = "^[0][9][5]{1}[0-9]{7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(employee.getPhone());
        boolean isFound = matcher.find();
        if (isFound) {
            System.out.println("Number is Vodafone");
            return true;
        } else {
            System.out.println("Number is not Vodafone");
            return false;
        }
    }*/

}
