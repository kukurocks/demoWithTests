package com.example.demowithtests.service;

import com.example.demowithtests.domain.Passport;
import org.springframework.stereotype.Service;


public interface PassportService {

    Passport create(Passport passport);

    Passport getById(Integer id);

    Passport handOver(Integer id);

    Passport cancel(Passport passport);

    Passport addImage(Integer passportId, Integer imageId);
}
