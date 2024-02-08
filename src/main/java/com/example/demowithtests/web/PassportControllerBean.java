package com.example.demowithtests.web;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.ImageReadDto;
import com.example.demowithtests.dto.PassportDto;
import com.example.demowithtests.service.PassportServiceBean;
import com.example.demowithtests.util.config.mapstruct.PassportMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class PassportControllerBean implements PassportController {

    private final PassportServiceBean passportService;
    private final PassportMapper mapper;

    @Override
    public PassportDto create (PassportDto passportDto) {
        Passport passport = mapper.toPassport(passportDto);
        final var passportTemp = passportService.create(passport);
        return mapper.toDto(passportTemp);
    }

    @Override
    public PassportDto getPassportById(Integer id) {
        Passport passport = passportService.getById(id);
        return mapper.toDto(passport);
    }

    @Override
    public PassportDto addPassportImage(ImageReadDto imageReadDto) {
        var imageId = imageReadDto.imageId();
        var passportId = imageReadDto.passportId();
        var passport = passportService.addImage(imageId, passportId);
        return mapper.toDto(passport);
    }
}