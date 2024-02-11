package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.PassportReadDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.service.WorkPlaceService;
import com.example.demowithtests.util.annotation.Profiler;
import com.example.demowithtests.util.config.mapstruct.EmployeeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
@AllArgsConstructor
@Slf4j
public class EmployeeControllerBean implements EmployeeController {

    private final EmployeeMapper mapper;
    private final EmployeeService employeeService;


    @Override
    public Employee addEmployeeWorkPlace(Integer empId, Integer wpId) {

        return employeeService.addWorkPlace(empId,wpId);
    }

    @Override
    public EmployeeDto setPassportForEmployee(PassportReadDto passportReadDto) {
        return mapper.toDto(employeeService.handPassport(
                passportReadDto.userId(), passportReadDto.passportId())) ;
    }

    @Override
    public EmployeeDto cancelPassport(Integer empId) {
        return mapper.toDto(employeeService.cancelPassport(empId));
    }

    public EmployeeReadDto saveEmployee(EmployeeDto requestForSave) {

        var employee = EmployeeMapper.INSTANCE.fromDto(requestForSave);

        return EmployeeMapper.INSTANCE.toReadDto(employeeService.create(employee));
    }


    public void saveEmployee1(Employee employee) {

        employeeService.create(employee);

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
        var dto = EmployeeMapper.INSTANCE.toReadDto(employee);
        return dto;
    }


    public EmployeeDto refreshEmployee(Integer id, EmployeeDto employeeDto) {

        Employee employee = employeeService.getById(id);
        Employee updateEmployee = mapper.fromDto(employeeDto);


        employeeService.updateGenderById(employee.getId(), updateEmployee.getGender());
        employeeService.updateCountryById(employee.getId(), updateEmployee.getCountry());
        employeeService.updateEmailById(employee.getId(), updateEmployee.getEmail());
        employeeService.updateNameById(employee.getId(), updateEmployee.getName());

        return mapper.toDto(employee);
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
}
