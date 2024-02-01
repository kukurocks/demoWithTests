package com.example.demowithtests.web;

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


    /**
     * Saves the given employee in the database.
     *
     * @param requestForSave the employee object to be saved
     * @return the saved employee object as EmployeeReadDto
     */
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

    /**
     * Saves the given employee in the database.
     *
     * @param employee the employee to be saved
     */
    @PostMapping("/usersS")
    @ResponseStatus(HttpStatus.CREATED)
    void saveEmployee1(@RequestBody Employee employee);

    /**
     * Retrieves a list of all users.
     *
     * @return a list of EmployeeReadDto objects representing the users
     */
    //get list of users
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all employees.",
            description = "Request to read all employees", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. No employees in data base")})
    List<EmployeeReadDto> getAllUsers();

    /**
     * Retrieves a page of employees based on the provided page number and size.
     *
     * @param page the page number to retrieve, defaults to 0 if not provided
     * @param size the page size, defaults to 5 if not provided
     * @return a Page object containing the employees
     */
    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all employees and paginates.",
            description = "Request to read all employees and paginates", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. No employees in data base")})
    Page<Employee> getPage(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int size);

    /**
     * Retrieves an employee by their ID.
     *
     * @param id the ID of the employee to retrieve
     * @return the employee object as an EmployeeReadDto
     */
    //get user by id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    EmployeeReadDto getEmployeeById(@PathVariable Integer id);

    /**
     * Refreshes an employee's information in the database by their ID.
     *
     * @param id          the ID of the employee to refresh
     * @param employeeDto the updated employee information
     * @return the updated employee information as an EmployeeDto
     */
    //Update user
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates an employee's email by his id.",
            description = "Request to update an employee's email by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    EmployeeDto refreshEmployee(@PathVariable("id") Integer id, @RequestBody EmployeeDto employeeDto);

    /**
     * Deletes an employee from the database based on their ID.
     *
     * @param id the ID of the employee to be deleted
     *
     * @since 1.0
     */
    //Удаление по id
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes an employee by his id.",
            description = "Request to delete an employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "DELETED."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    void removeEmployeeById(@PathVariable Integer id);

    /**
     * Deletes all employees from the database.
     *
     * @since 1.0
     */
    //Удаление всех юзеров
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes all employees.",
            description = "Request to delete all employees", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "DELETED."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    void removeAllUsers();

    /**
     * Returns all employees by country and paginates.
     *
     * @param country   (optional) the country name to filter employees by
     * @param page      the page number to retrieve, defaults to 0 if not provided
     * @param size      the page size, defaults to 3 if not provided
     * @param sortList  the list of fields to sort by
     * @param sortOrder the sort order, defaults to DESC if not provided
     * @return a Page object containing the employees matching the specified country and paginated
     */
    @GetMapping("/users/country")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all employees by country and paginates.",
            description = "Request to read all employees by country and paginates", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. No employees in data base")})

    Page<Employee> findByCountry(@RequestParam(required = false) String country,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "3") int size,
                                 @RequestParam(defaultValue = "") List<String> sortList,
                                 @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder);

    /**
     * Retrieves a list of all countries of all employees.
     *
     * @return a list of String representing the countries
     *
     * @since 1.0
     */
    @GetMapping("/users/c")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all countries of all employees.",
            description = "Request to read all countries of all employees", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. No employees in data base")})
    List<String> getAllUsersC();

    /**
     * Retrieves a list of countries sorted by name.
     *
     * @return a list of countries sorted by name
     *
     * @since 1.0
     */
    @GetMapping("/users/s")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns a list of countries sorted by name.",
            description = "Request to read a list of countries sorted by name.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. No employees in data base")})
    List<String> getAllUsersSort();

    /**
     * Retrieves a list of emails of all employees.
     *
     * @return Optional<String> representing the list of emails. Returns an empty Optional if there are no employees in the database.
     *
     * @throws InaccessibleDatabaseException if there is an issue accessing the database.
     * @throws UnauthorizedAccessException if the user is not authorized to access the employee emails.
     *
     * @since 1.0.0
     */
    @GetMapping("/users/emails")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns a list of emails of all employees.",
            description = "Request to read all emails of all employees.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. No employees in data base")})
    Optional<String> getAllUsersSo();

    /**
     * Retrieves a list of all employee records that do not have an email address.
     *
     * @return A list of EmployeeReadDto objects representing the retrieved employees without an email.
     *
     * @GetMapping("/emails") annotation specifies that this method handles HTTP GET requests to the "/emails" endpoint.
     * @ResponseStatus(HttpStatus.OK) annotation sets the HTTP response status code to 200 (OK) for a successful operation.
     * @Operation(summary = "Returns all employees filtered by country.") annotation provides a summary of the method's functionality.
     * @ApiResponses annotation specifies the possible HTTP responses and their descriptions.
     * - @ApiResponse(responseCode = "201", description = "OK. pam pam param.") - A successful response with a status code of 201 and description "OK. pam pam param."
     * - @ApiResponse(responseCode = "400", description = "Invalid input") - An error response with a status code of 400 and description "Invalid input" when the input is invalid.
     * - @ApiResponse(responseCode = "404", description = "NOT FOUND. No employees in data base") - An error response with a status code of 404 and description "NOT FOUND. No employees
     * in data base" when no employees are found in the database.
     *
     * This method queries the database to retrieve all employee records that do not have an email address and returns them as a list of EmployeeReadDto objects.
     */
    @GetMapping("/emails")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all employees filtered by country.",
            description = "Request to read all employees filtered by country", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. No employees in data base")})
    List<EmployeeReadDto> getAllUsersWithoutEmail();

    /**
     * Retrieves an employee based on the specified email address.
     *
     * @param email The email address of the employee. If not provided, "n@gmail.com" will be used as default.
     * @return The employee whose email matches the specified email address or null if no employee found.
     */
    @GetMapping("/emails/by")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all employees whose email is null.",
            description = "Request to read all employees whose email is null", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. No employees in data base")})
    Employee getUserByEmail(@RequestParam(defaultValue = "n@gmail.com") String email);


    /**
     * Returns all employees whose country name starts with a lowercase letter.
     *
     * Request to read all employees whose country name starts with a lowercase letter.
     *
     * @return a list of EmployeeReadDto objects representing the employees whose country name starts with a lowercase letter.
     * @throws NotFoundException if there are no employees in the database.
     */
    @GetMapping("/country")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all employees whose country name starts with a lowercase letter.",
            description = "Request to read all employees whose country name starts with a lowercase letter",
            tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. No employees in data base")})
    List<EmployeeReadDto> findAllCountry();

    /**
     * Retrieves a list of employees by country.
     *
     * @param country The desired country to filter the employees by.
     * @return A list of employee objects that belong to the specified country.
     */
    @GetMapping("/users/countryBy")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates country names of all employees to uppercase first letter.",
            description = "Request to update country names of all employees to uppercase first letter",
            tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. No employees in data base")})
    List<Employee> getByCountry(@RequestParam() String country);
}


