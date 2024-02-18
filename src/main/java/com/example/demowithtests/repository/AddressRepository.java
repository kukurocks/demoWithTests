package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query(value = "select * from addresses as a where a.employee_id=:id", nativeQuery = true)
    List<Address> findAddressByEmployeeId(Integer id);


}
