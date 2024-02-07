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
    public Passport hand(Integer id) {

        Passport passport = passportRepository.findById(id).orElseThrow(
                () -> new PassportIsHandedException("Passport with id = " + id + " does not exist"));
        if (passport.getIsHanded()) {
            throw new PassportIsHandedException("The passport with id = " + id + " has already been handed");
        }
        if (passport.getImage() == null) {
            throw new PassportIsHandedException("The passport image for id = " + id + " is not available");
        }

        passport.setIsHanded(true);
        return passportRepository.save(passport);
    }

    @Override
    public Passport cancel(Integer id) {
        return null;
    }

    @Override
    public Passport addImage(Integer imageId, Integer passportId) {
        Passport passport = passportRepository.findById(passportId).orElseThrow();
        Image image = imageRepository.findById(imageId).orElseThrow();
        passport.setImage(image);
        return passportRepository.save(passport);
    }
}
