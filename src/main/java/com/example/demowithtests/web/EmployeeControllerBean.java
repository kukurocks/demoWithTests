package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.PassportReadDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.util.config.mapstruct.EmployeeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
public class EmployeeControllerBean implements EmployeeController {

    private final EmployeeMapper mapper;
    private final EmployeeService employeeService;


    @Override
    public Page<Employee> readActiveAddressesByCountry(String country, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        return employeeService.getActiveAddressesByCountry(country, pageable);
    }

    @Override
    public Employee addEmployeeWorkPlace(Integer empId, Integer wpId) {

        return employeeService.addWorkPlace(empId, wpId);
    }

    @Override
    public EmployeeDto setPassportForEmployee(PassportReadDto passportReadDto) {

        return mapper.toDto(employeeService.handPassport(passportReadDto.userId(), passportReadDto.passportId()));

    }

    @Override
    public EmployeeDto cancelPassport(Integer empId) {
        return mapper.toDto(employeeService.cancelPassport(empId));
    }

    public EmployeeReadDto saveEmployee(EmployeeDto requestForSave) {

        var employee = EmployeeMapper.INSTANCE.fromDto(requestForSave);

        return EmployeeMapper.INSTANCE.toReadDto(employeeService.create(employee));
    }

    @Override
    public void saveEmployeeIfNameBigger(Integer employeeId) {

        employeeService.updateEM(employeeId);

    }

    @Override
    public void saveQuicklyEmployee(Integer employeeId) throws InterruptedException {

        employeeService.updateBeforeLongTermOperation(employeeId);
    }

    public List<EmployeeReadDto> getAllUsers() {


        return mapper.toListReadDto(employeeService.getAll());
    }


    public Page<Employee> getPage(int page,
                                  int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        return employeeService.getAllWithPagination(paging);
    }

    public EmployeeReadDto getEmployeeById(Integer id) {
        var employee = employeeService.getById(id);
        return EmployeeMapper.INSTANCE.toReadDto(employee);
    }


    public EmployeeDto refreshEmployee(Integer id, EmployeeDto employeeDto) {
        return mapper.toDto(employeeService.updateById(id, mapper.fromDto(employeeDto)));
    }

    @Override
    public List<Employee> getEmployeeByName(String name) {
        return employeeService.getByName(name);
    }


    public void removeEmployeeById(Integer id) {
        employeeService.removeById(id);
    }


    public void removeAllUsers() {
        employeeService.removeAll();
    }


    public Page<Employee> findByCountry(String country,
                                        int page,
                                        int size,
                                        List<String> sortList,
                                        Sort.Direction sortOrder) {
        //Pageable paging = PageRequest.of(page, size);
        //Pageable paging = PageRequest.of(page, size, Sort.by("name").ascending());
        return employeeService.findByCountryContaining(country, page, size, sortList, sortOrder.toString());
    }

    public List<String> getAllUsersC() {
        return employeeService.getAllEmployeeCountry();
    }

    public List<String> getAllUsersSort() {
        return employeeService.getSortCountry();
    }

    public Optional<String> getAllUsersSo() {
        return employeeService.findEmails();
    }

    public List<EmployeeReadDto> getAllUsersWithoutEmail() {
        List<Employee> list = employeeService.findAllByEmailIsNull();

        return EmployeeMapper.INSTANCE.toListReadDto(list);

    }

    public Employee getUserByEmail(String email) {

        return employeeService.findEmployeeByEmail(email);
    }

    public List<EmployeeReadDto> findAllCountry() {

        List<Employee> allWithSyntaxError = employeeService.findAllWithSyntaxError();

        return EmployeeMapper.INSTANCE.toListReadDto(allWithSyntaxError);
    }

    public List<Employee> getByCountry(String country) {
        return employeeService.filterByCountry(country);
    }

    @Override
    public List<String[]> findEmailLength(String employee, String sortBY) {
        return employeeService.findEmployeeWithThemEmailLength(employee, sortBY);

    }
}
