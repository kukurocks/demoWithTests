package com.example.demowithtests.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public record AddressResponseDto (

        @Schema(description = "Name of the country.", example = "England")
        String country,
        @Schema(description = "Name of the city.", example = "London")
        String city,
        @Schema(description = "Name of the street.", example = "Downing Street")
        String street
){}







