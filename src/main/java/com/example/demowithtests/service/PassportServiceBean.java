package com.example.demowithtests.service;

import com.example.demowithtests.domain.Action;
import com.example.demowithtests.domain.Image;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.PassportEvent;
import com.example.demowithtests.repository.ImageRepository;
import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.util.exception.PassportIsHandedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class PassportServiceBean implements PassportService {

    private final PassportRepository passportRepository;
    private final ImageRepository imageRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Passport create(Passport passport) {

        return passportRepository.save(passport);
    }

    @Override
    public Passport getById(Integer id) {
        return passportRepository.findById(id).orElseThrow();
    }

    @Override
    public Passport handOver(Integer id) {

        Passport passport = passportRepository.findById(id).orElseThrow();
        if (passport.getIsHanded()) {
            throw new PassportIsHandedException("Passport with id:" + id + " has already been handed");
        }
       /* if (passport.getImage() == null) {
            throw new PassportIsHandedException("Passport with id:" + id + " without photo");
        }*/
        passport.setIsHanded(true);
        passport.setExpireDate(LocalDateTime.now().plusYears(5L));

        return passportRepository.save(passport);
    }

    @Override
    public void cancel(Passport passport) {

        if (!passport.getIsHanded()) {
            throw new PassportIsHandedException("Cannot cancel a handed passport");
        }
        passport.setIsHanded(false);

        passportRepository.save(passport);
    }

    @Override
    public Passport addImage(Integer imageId, Integer passportId) {
        Passport passport = passportRepository.findById(passportId).orElseThrow();
        Image image = imageRepository.findById(imageId).orElseThrow();
        passport.setImage(image);
        return passportRepository.save(passport);
    }

    @Override
    public Map<Integer,List<PassportEvent>> getHistoryByEmployeePassport(Integer emplId) {

        List<PassportEvent> passportEvents = passportRepository.getHistoryByEmployee(emplId);
        Map<Integer, List<PassportEvent>> historyByEmployeePassport = new HashMap<>();
        historyByEmployeePassport.put(passportRepository.findByEmployeeId(emplId), passportEvents);
        return historyByEmployeePassport;
    }

    @Override
    @Transactional
    public void deleteAllWithEntityManager() {
        entityManager.createQuery("DELETE FROM Passport").executeUpdate();
    }
}
