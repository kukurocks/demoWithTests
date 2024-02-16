package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.PassportEvent;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.repository.PassportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PassportHistoryServiceBean implements PassportHistoryService {

    PassportRepository passportRepository;
    EmployeeRepository employeeRepository;

    @Override
    public void createHistory(PassportEvent event) {
       Employee employee = employeeRepository.findById(event.getEmployeeId()).orElseThrow();
        Passport passport = employee.getPassport();
        passport.setPassportEvent(event);
        passportRepository.save(passport);
    }

    @Override
    public void updateHistory(PassportEvent event) {
        Passport passport = Passport.builder().passportEvent(event).build();
        passportRepository.save(passport);
    }

}
