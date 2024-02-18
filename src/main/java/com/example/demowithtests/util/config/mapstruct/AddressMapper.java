package com.example.demowithtests.util.config.mapstruct;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.dto.address.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDto toDto(Address address);
}
