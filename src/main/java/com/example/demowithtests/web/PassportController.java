package com.example.demowithtests.web;

import com.example.demowithtests.domain.PassportEvent;
import com.example.demowithtests.dto.ImageReadDto;
import com.example.demowithtests.dto.PassportDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PassportController {
    @PostMapping("/passport")
    PassportDto create(@RequestBody @Valid PassportDto passportDto);
    @GetMapping("/passport/{id}")
    PassportDto getPassportById(@PathVariable Integer id);

    @GetMapping("/passport/history/{emplId}")
    Map<Integer,List<PassportEvent>> getHistory(@PathVariable(value = "emplId") Integer emplId);

    @PatchMapping("/passport/image")
    PassportDto addPassportImage(@RequestBody ImageReadDto imageReadDto);

    @DeleteMapping("passport/delete_all")
    void deleteAll();

}