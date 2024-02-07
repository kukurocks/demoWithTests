package com.example.demowithtests.service;

import com.example.demowithtests.domain.Passport;
import org.springframework.stereotype.Service;


public interface PassportService {

    Passport create(Passport passport);

    Passport getById(Integer id);

    Passport hand(Integer id);

    Passport cancel(Integer id);

    Passport addImage(Integer passportId, Integer imageId);
}
