package com.example.demowithtests.repository;

import com.example.demowithtests.domain.EmployeesWorkPlaces;
import com.example.demowithtests.domain.EmployeesWorkPlacesKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesWorkPlacesRepository extends JpaRepository<EmployeesWorkPlaces, EmployeesWorkPlacesKey> {

    @Query("select count(*) from EmployeesWorkPlaces where workPlace.id = :id and isActive=true")
    Integer selectCountOfActiveWorkPlacesById(Integer id);

    @Query("select count (*) from EmployeesWorkPlaces where employee.id=:empId and isActive=true")
    Integer selectCountHeldWorkPlaceOfEmployeeById(Integer empId);
}
