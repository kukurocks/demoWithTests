package com.example.demowithtests.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class PassportReadDto {

    @Schema(description = "User id with employee")
    Integer userId;

    @Schema(description = "Passport id to be issued")
    Integer passportId;
}
