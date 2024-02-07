package com.example.demowithtests.service;

import com.example.demowithtests.domain.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    Image uploadImage(MultipartFile imageFile) throws IOException;

    Image updateImage(Integer id, MultipartFile imageFile) throws IOException;

    Image getImageById(Integer id);

    byte[] downloadImage(String imageName);

    void delete(Integer id);
}
