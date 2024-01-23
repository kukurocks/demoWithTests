package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class AddressServiceBean implements AddressService{

    private AddressRepository addressRepository;

    @Autowired
    public AddressServiceBean(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public  List<String> getAllById(Integer id) {
       return addressRepository.findAllById(id);
    }
}
