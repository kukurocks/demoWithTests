package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {

    @Query("select e.id, e.name, a.addressHasActive from Employee as e left outer join e.addresses as a where e.id = ?1")
    List<String> findAllById(Integer id);

}
