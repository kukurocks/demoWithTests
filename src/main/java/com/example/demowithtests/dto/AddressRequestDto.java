package com.example.demowithtests.dto;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

//@Accessors(chain = true)

public record AddressRequestDto (
        Long id,

        Boolean addressHasActive,

        String country,

        String city,

        String street,

        //todo: something Jira - 5544

        @CreationTimestamp
        LocalDate date
){


    public AddressRequestDto{
        }

}


