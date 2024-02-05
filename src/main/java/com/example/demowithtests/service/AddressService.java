package com.example.demowithtests.service;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.AddressResponseDto;

import java.util.List;
import java.util.Map;

public interface AddressService {
    List<Address> getAddressesByEmployeeId(Integer id);

    List<Address> findAll();

    List<Address> findByEmployee(Employee employee);

}
