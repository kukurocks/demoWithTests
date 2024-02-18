package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.dto.address.AddressDto;
import com.example.demowithtests.util.annotation.dto.BlockedEmailDomains;
import com.example.demowithtests.util.annotation.dto.Severity;
import com.example.demowithtests.util.annotation.entity.Name;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class EmployeeDto {

    @Name
    @NotNull
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    public String name;

    // @CountryRightFormed
    @Schema(description = "Name of the country.", example = "England", required = true)
    public String country;

    public Boolean deleted = Boolean.FALSE;

    @Email
    @NotNull
    @BlockedEmailDomains(payload = Severity.Error.class)
    @Schema(description = "Email address of an employee.", example = "billys@mail.com", required = true)
    public String email;

    public Gender gender;

    public Set<AddressDto> addresses = new HashSet<>();
}
