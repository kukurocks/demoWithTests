package com.example.demowithtests.web;

import com.electronwill.nightconfig.core.conversion.Path;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Tag(name = "Employee", description = "Employee API")
public interface EmployeeController {


    //save user in db
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    EmployeeReadDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave);

    @PostMapping("/usersS")
    @ResponseStatus(HttpStatus.CREATED)
    void saveEmployee1(@RequestBody Employee employee);

    //get list of users
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    List<EmployeeReadDto> getAllUsers();

    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    Page<Employee> getPage(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int size);

    //get user by id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    EmployeeReadDto getEmployeeById(@PathVariable(name = "id") Integer id);

    //Update user
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    EmployeeDto refreshEmployee(@PathVariable("id") Integer id, @RequestBody EmployeeDto employeeDto);

    @GetMapping("/users/")
    @ResponseStatus(HttpStatus.OK)
    List<Employee> getEmployeeByName(@RequestParam(name = "name") String name);

    //Удаление по id
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeEmployeeById(@PathVariable Integer id);

    //Удаление всех юзеров
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeAllUsers();

    @GetMapping("/users/country")
    @ResponseStatus(HttpStatus.OK)
    Page<Employee> findByCountry(@RequestParam(required = false) String country,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "3") int size,
                                 @RequestParam(defaultValue = "") List<String> sortList,
                                 @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder);

    @GetMapping("/users/c")
    @ResponseStatus(HttpStatus.OK)
    List<String> getAllUsersC();

    @GetMapping("/users/s")
    @ResponseStatus(HttpStatus.OK)
    List<String> getAllUsersSort();

    @GetMapping("/users/emails")
    @ResponseStatus(HttpStatus.OK)
    Optional<String> getAllUsersSo();

    @GetMapping("/emails")
    @ResponseStatus(HttpStatus.OK)
    List<EmployeeReadDto> getAllUsersWithoutEmail();

    @GetMapping("/emails/by")
    @ResponseStatus(HttpStatus.OK)
    Employee getUserByEmail(@RequestParam(defaultValue = "n@gmail.com") String email);


    @GetMapping("/country")
    @ResponseStatus(HttpStatus.OK)
    List<EmployeeReadDto> findAllCountry();

    @GetMapping("/users/countryBy")
    @ResponseStatus(HttpStatus.OK)
    List<Employee> getByCountry(@RequestParam String country);

    @GetMapping ("users/lengthEmail")
    @ResponseStatus(HttpStatus.OK)
    List<String[]> findEmailLength(@RequestParam(name = "name") String employee, @RequestParam(name = "sortBy", defaultValue = "name") String sortBY);

}


