package com.example.demowithtests.web;

import com.example.demowithtests.domain.Image;
import com.example.demowithtests.dto.ImageDto;
import com.example.demowithtests.service.ImageService;
import com.example.demowithtests.util.config.mapstruct.ImageMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@AllArgsConstructor
public class ImageControllerBean implements ImageController {

    private final ImageService imageService;
    private final ImageMapper mapper;

    @Override
    public ImageDto uploadImage(MultipartFile file) throws IOException {
        Image uploadImage = imageService.uploadImage(file);
        return mapper.toDto(uploadImage);
    }

    @Override
    public ImageDto getImageById(Integer id) {
        Image image = imageService.getImageById(id);
        return mapper.toDto(image);
    }

    @Override
    public ImageDto updateImage(Integer id, MultipartFile imageFile) throws IOException {
        Image image = imageService.updateImage(id, imageFile);
        return mapper.toDto(image);
    }

    @Override
    public ResponseEntity<?> downloadImage(String fileName) {
        byte[] imageData = imageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(imageData);
    }
}