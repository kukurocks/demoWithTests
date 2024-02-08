package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.ImageReadDto;
import com.example.demowithtests.dto.PassportDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PassportController {
    @PostMapping("/passport")
    PassportDto create(@RequestBody @Valid PassportDto passportDto);
    @GetMapping("/passport/{id}")
    PassportDto getPassportById(@PathVariable Integer id);

    @PatchMapping("/passport/image")
    PassportDto addPassportImage(@RequestBody ImageReadDto imageReadDto);

}