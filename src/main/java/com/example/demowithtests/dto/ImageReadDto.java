package com.example.demowithtests.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ImageReadDto(
        @Schema(description = "Image ID", example = "1")
        Integer imageId,

        @Schema(description = "Passport id to be issued")
        Integer passportId
) { }