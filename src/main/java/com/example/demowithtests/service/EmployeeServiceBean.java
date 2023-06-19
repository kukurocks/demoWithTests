package com.example.demowithtests.service;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.util.exception.ListEmptyException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import com.example.demowithtests.util.exception.ResourceWasDeletedException;
import com.example.demowithtests.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @PersistenceContext
    private EntityManager entityManager;



    @Override
   // @Transactional(propagation = Propagation.MANDATORY)
    public Employee create(Employee employee) {
        Set<Address> addresses = employee.getAddresses();
        for(Address a: addresses){
            a.setCountry(employee.getCountry());}
        return employeeRepository.save(employee);
    }

    @Override
    public Employee createEM(Employee employee) {
        return entityManager.merge(employee);
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> list = employeeRepository.findAll();
        List<Employee> resultList = new ArrayList<>();
        if(!list.isEmpty()){
            for(Employee e : list){
                if(!e.isDeleted()){
                    resultList.add(e);
                }
            }
           if(resultList.isEmpty()) {
            throw new ListEmptyException();}
        }

        return resultList;
    }

    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        log.debug("getAllWithPagination() - start: pageable = {}", pageable);
        Page<Employee> list = employeeRepository.findAll(pageable);
        log.debug("getAllWithPagination() - end: list = {}", list);
        return list;
    }

    @Override
    public Employee getById(Integer id) {
        var employee = employeeRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
               // .orElseThrow(ResourceNotFoundException::new);
         if (employee.isDeleted()) {
            throw new ResourceWasDeletedException();
        }
        return employee;
    }

    @Override
    public Employee updateById(Integer id, Employee employee) {
        return employeeRepository.findById(id)
                .map(entity -> {
                    entity.setName(employee.getName());
                    entity.setEmail(employee.getEmail());
                    entity.setCountry(employee.getCountry());
                    entity.setGender(employee.getGender());
                    return employeeRepository.save(entity);
                })
                .orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public void removeById(Integer id) {

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        // .orElseThrow(ResourceNotFoundException::new);
        if (employee.isDeleted()) {
            throw new ResourceWasDeletedException();
        }

        employee.setDeleted(true);
        // employeeRepository.delete(employee.get());
        employeeRepository.save(employee);




    }
        //repository.deleteById(id);
       // var employee = employeeRepository.findById(id).orElseThrow();
        /*if(!employee.isPresent()){
            throw new UserNotFoundException();

        }
         if(!employee.get().isDeleted()){
             throw new ResourceWasDeletedException();

           }*/
                // .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));











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
    }*/

    /*public boolean isVodafone(Employee employee) {
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

    @Override
    public void removeAll() {
       List<Employee> list = employeeRepository.findAll();
       if(!list.isEmpty()){
          for(Employee e:list){
              e.setDeleted(true);
              employeeRepository.save(e);
          }
       }
       else{throw new ListEmptyException();}
    }

    /*@Override
    public Page<Employee> findByCountryContaining(String country, Pageable pageable) {
        return employeeRepository.findByCountryContaining(country, pageable);
    }*/

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
                .map(country -> country.getCountry())
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
                .collect(Collectors.toList());

        var opt = emails.stream()
                .filter(s -> s.endsWith(".com"))
                .findFirst()
                .orElse("error?");
        return Optional.ofNullable(opt);
    }

    @Override
    public List<Employee> filterByCountry(String country) {
        return employeeRepository.findByCountry(country);
    }

    @Override
    public List<Employee> findAllByEmailIsNull() {

            return employeeRepository.findAllByEmailIsNull()
                    .stream()
                    .filter(e -> !e.isDeleted())
                    .collect(Collectors.toList());


    }
    @Override
    public List<Employee> findAllWithSyntaxErorr(){

        List<Employee> allEmp = employeeRepository.findAll();
       List<Employee> resultList = new ArrayList<>();

        for(Employee e : allEmp){
            String country = e.getCountry();
            if(isLowerCase(country)){
               e.setCountry(capitalizeString(country));
               resultList.add(e);
               employeeRepository.save(e);
            }
        }
        return resultList;
    }

    public boolean isLowerCase(String str) {
        return str.equals(str.toLowerCase());
    }
    public String capitalizeString(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        String correctedString = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        return correctedString;
    }

}
