package com.example.demowithtests.util.config.mapstruct;


import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.PassportDto;
import com.example.demowithtests.dto.PassportReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface PassportMapper {
    PassportMapper INSTANCE = Mappers.getMapper(PassportMapper.class);

    @Mapping(source = "passport.image", target = "imageDto")
    PassportDto toDto (Passport passport);

    PassportReadDto toReadDto (Passport passport);

    Passport toPassport(PassportDto dto);

}
