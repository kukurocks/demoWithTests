package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.dto.address.AddressReadDto;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


public class EmployeeReadDto {

    public Integer id;
    @NotNull(message = "Name may not be null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @Schema(description = "Name of an employee.", example = "Billy")
    public String name;

    public String country;

    @Email
    @NotNull
    public String email;

    public Set<AddressReadDto> addresses = new HashSet<>();
    public Gender gender;
}
