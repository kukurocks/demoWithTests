package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.PassportEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Integer> {

    @Query("select p.passportEvent from Passport p where p.passportEvent.employeeId = :emplId")
    List<PassportEvent> getHistoryByEmployee(Integer emplId);

   @Query("select p.id from Passport p where p.passportEvent.employeeId = :emplId and p.passportEvent.action='HANDOVER'")
    Integer findByEmployeeId(Integer emplId);
}
