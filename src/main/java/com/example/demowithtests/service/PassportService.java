package com.example.demowithtests.service;

import com.example.demowithtests.domain.Passport;


public interface PassportService {

    Passport create(Passport passport);

    Passport getById(Integer id);

    Passport handOver(Integer id);

    void cancel(Passport passport);

    Passport addImage(Integer passportId, Integer imageId);

}
