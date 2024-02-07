package com.example.demowithtests.web;

import com.example.demowithtests.dto.ImageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping(value = "/api/image")
public interface ImageController {
    @PostMapping
    ImageDto uploadImage(@RequestParam("image") MultipartFile file) throws IOException;

    @GetMapping("/{id}")
    ImageDto getImageById(@PathVariable Integer id);

    @PutMapping("/{id}")
    ImageDto updateImage(@PathVariable Integer id, @RequestParam("file") MultipartFile imageFile) throws IOException;

    @GetMapping("/photo/{fileName}")
    ResponseEntity<?> downloadImage(@PathVariable String fileName);
}

