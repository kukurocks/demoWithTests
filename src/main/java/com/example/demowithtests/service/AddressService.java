package com.example.demowithtests.service;

import com.example.demowithtests.domain.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAddressesByEmployeeId(Integer id);

    List<Address> findAll();


}
