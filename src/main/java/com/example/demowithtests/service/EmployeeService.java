package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {


    Employee addWorkPlace(Integer empId, Integer wpId);

    Employee  cancelPassport(Integer empId);

    Employee handPassport(Integer employeeId, Integer passportId);

    Employee create(Employee employee);

    @Transactional
    Employee updateBeforeLongTermOperation(Integer updatedEmployeeId) throws InterruptedException;
    @Transactional
    Employee updateEM(Integer updatedEmployeeId);

    List<Employee> getAll();

    Page<Employee> getAllWithPagination(Pageable pageable);

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

   void removeById(Integer id);

    void removeAll();

    //Page<Employee> findByCountryContaining(String country, Pageable pageable);
    /**
     * @param country  Filter for the country if required
     * @param page            number of the page returned
     * @param size            number of entries in each page
     * @param sortList        list of columns to sort on
     * @param sortOrder       sort order. Can be ASC or DESC
     * @return Page object with customers after filtering and sorting
     */
    Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder);

    /**
     * Get all the countries of all the employees.
     *
     * @return A list of all the countries that employees are from.
     */
    List<String> getAllEmployeeCountry();

    /**
     * It returns a list of countries sorted by name.
     *
     * @return A list of countries in alphabetical order.
     */
    List<String> getSortCountry();

    Optional<String> findEmails();

    List<Employee> filterByCountry(String country);

    List<Employee> findAllByEmailIsNull();
     List<Employee> findAllWithSyntaxError();

    Employee findEmployeeByEmail(String email);

   Page<Employee> getActiveAddressesByCountry(String country, Pageable pageable);

     List<String[]> findEmployeeWithThemEmailLength (String employee, String sortBY);

    List<Employee> getByName (String name);

}
