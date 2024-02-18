package com.example.demowithtests.web;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.dto.address.AddressDto;
import com.example.demowithtests.service.AddressService;
import com.example.demowithtests.util.config.mapstruct.AddressMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AddressControllerBean implements AddressController{

    private final AddressService addressService;
    private final AddressMapper mapper;

    @Override
    public List<Address> getAddressByEmpId(Integer id) {
        return addressService.getAddressesByEmployeeId(id);
    }


    @Override
    public List<AddressDto> getAllAddresses() {
       List<Address> addresses = addressService.findAll();

        return addresses.stream().map(mapper::toDto).toList();
    }
}
