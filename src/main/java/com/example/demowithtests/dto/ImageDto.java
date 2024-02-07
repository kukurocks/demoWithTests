package com.example.demowithtests.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ImageDto(
        @Schema(description = "Image id", example = "1")
        Long id,

        @Schema(description = "Image name", example = "name_image.jpg")
        String name,

        @Schema(description = "Image type", example = "image/jpeg")
        String type) {
}