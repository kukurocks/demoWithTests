package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;



public record EmployeeReadDto(
        Integer id,
        @NotNull(message = "Name may not be null")
        @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
        @Schema(description = "Name of an employee.", example = "Billy", required = true)
        String name,

        String country,

        @Email
        @NotNull
        String email,

        Set<AddressResponseDto> addresses,
        Gender gender

) {
    @Builder
    public EmployeeReadDto {

    }

}
