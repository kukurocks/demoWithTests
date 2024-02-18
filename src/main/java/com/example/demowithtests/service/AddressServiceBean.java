package com.example.demowithtests.service;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressServiceBean implements AddressService {

   @Autowired
    private AddressRepository addressRepository;


    @Override
    public List<Address> getAddressesByEmployeeId(Integer id) {

        return addressRepository.findAddressByEmployeeId(id);
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public List<Address> findByEmployee(Employee employee) {
        return addressRepository.findByEmployee(employee);
    }


}
