package com.example.demowithtests.web;


import com.example.demowithtests.domain.Address;
import com.example.demowithtests.dto.address.AddressDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Address", description = "Employee API")

public interface AddressController {

    @GetMapping("/address/{id}")
    @ResponseStatus(HttpStatus.OK)
    List<Address> getAddressByEmpId(@PathVariable Integer id);


    @GetMapping("/addresses")
    @ResponseStatus(HttpStatus.OK)
    List<AddressDto> getAllAddresses();
}
