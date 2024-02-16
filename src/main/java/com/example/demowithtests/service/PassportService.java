package com.example.demowithtests.service;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.PassportEvent;

import java.util.List;
import java.util.Map;


public interface PassportService {

    Passport create(Passport passport);

    Passport getById(Integer id);

    Passport handOver(Integer id);

    void cancel(Passport passport);

    Passport addImage(Integer passportId, Integer imageId);

   Map<Integer,List<PassportEvent>>  getHistoryByEmployeePassport(Integer emplId);

   void deleteAllWithEntityManager();


}
