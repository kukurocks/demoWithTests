package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    //@Query(value = "SELECT * FROM users", nativeQuery = true)

    @Query(value = "select e from Employee e where e.country =?1")
    List<Employee> findByCountry(String country);

    @Query(value = "select * from users join addresses on users.id = addresses.employee_id " +
            "where users.gender = :gender and addresses.country = :country", nativeQuery = true)
    List<Employee> findByGender(String gender, String country);

    @Query("SELECT e FROM Employee e WHERE e.name LIKE %?1%")
   List<Employee>  findByName(String name);

    Employee findEmployeeByEmail(String email);

    @NonNull
    Page<Employee> findAll(@NonNull Pageable pageable);

    Page<Employee> findByName(String name, Pageable pageable);

    Page<Employee> findByCountryContaining(String country, Pageable pageable);

    List<Employee> findAllByEmailIsNull();

    @Query("select e.name, LENGTH(e.email) as email_length from #{#entityName} e where e.name like %?1%")
    List<String[]> findByAsArrayAndSort(String text, Sort sort);




}
