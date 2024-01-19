package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    //@Query(value = "SELECT * FROM users", nativeQuery = true)

    @Query(value = "select e from Employee e where e.country =?1")
    List<Employee> findByCountry(String country);

    @Query(value = "select * from users_1 join addresses on users_1.id = addresses.employee_id " +
            "where users_1.gender = :gender and addresses.country = :country", nativeQuery = true)
    List<Employee> findByGender(String gender, String country);

    Employee findByName(String name);

    Employee findEmployeeByEmail(String email);

    @NonNull
    Page<Employee> findAll(@NonNull Pageable pageable);

    Page<Employee> findByName(String name, Pageable pageable);

    Page<Employee> findByCountryContaining(String country, Pageable pageable);

    List<Employee> findAllByEmailIsNull();

    @Modifying
    @Transactional
    @Query(value = "update users_1 " +
            "set country = regexp_replace(country, left(country, 1), upper(left(country, 1))) " +
            "where country ~ '^[a-z]|^[а-я]'", nativeQuery = true)
    void updateLowerCaseCountriesToUpperCase();




}
