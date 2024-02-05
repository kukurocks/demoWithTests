package com.example.demowithtests.web;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.AddressResponseDto;
import com.example.demowithtests.service.AddressService;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.util.config.mapstruct.AddressMapper;
import com.example.demowithtests.util.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AddressControllerBean implements AddressController{

    private final EmployeeService employeeService;
    private final AddressService addressService;
    private final AddressMapper mapper;

    @Override
    public List<Address> getAddressByEmpId(Integer id) {
        return addressService.getAddressesByEmployeeId(id);
    }

    @Override
    public List<Address> findByEmployee(Integer employee) {
        Employee theEmployee = employeeService.getById(employee);
        return addressService.findByEmployee(theEmployee);
    }


    @Override
    public List<AddressResponseDto> getAllAddresses() {
       List<Address> addresses = addressService.findAll();

        return addresses.stream().map(mapper::toDto).toList();
    }
}
