package com.example.demowithtests.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

public record PassportDto (
        @Schema(description = "Passport ID", example = "1")
        Integer id,

        @Schema(description = "Passport number", example = "1234")
        String number,

        @Schema(description = "Passport series", example = "EA")
        String series,

        @Schema(description = "Passport expire Date", example = "2023-01-01T12:12:12")
        LocalDateTime expireDate,

        @Schema(description = "UUID of the Passport")
        String uuid,

        @Schema(description = "Image associated with the passport")
        ImageDto imageDto
){
    @Builder
    public PassportDto{
        uuid = UUID.randomUUID().toString();
    }
}
