package com.example.demowithtests.service;

import com.example.demowithtests.domain.Image;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.repository.ImageRepository;
import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.util.exception.PassportIsHandedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PassportServiceBean implements PassportService {

    private final PassportRepository passportRepository;
    private final ImageRepository imageRepository;

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
        if (passport.getImage() == null) {
            throw new PassportIsHandedException("Passport with id:" + id + " without photo");
        }

        passport.setIsHanded(true);
        return passportRepository.save(passport);
    }

    @Override
    public Passport cancel(Passport passport) {
        if(passport.getPreviousOwner()!=null){
            throw new PassportIsHandedException("The passport has already been canceled");
        }
        passport.setPreviousOwner(passport.getId());
        return passportRepository.save(passport);
    }

    @Override
    public Passport addImage(Integer imageId, Integer passportId) {
        Passport passport = passportRepository.findById(passportId).orElseThrow();
        Image image = imageRepository.findById(imageId).orElseThrow();
        passport.setImage(image);
        return passportRepository.save(passport);
    }
}
