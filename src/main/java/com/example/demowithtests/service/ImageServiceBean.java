package com.example.demowithtests.service;

import com.example.demowithtests.domain.Image;
import com.example.demowithtests.repository.ImageRepository;
import com.example.demowithtests.util.ImageUtils;
import com.example.demowithtests.util.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class ImageServiceBean implements ImageService{

    private final ImageRepository imageRepository;
    @Override
    public Image uploadImage(MultipartFile imageFile) throws IOException {
        var imageToSave = Image.builder()
                .name(imageFile.getOriginalFilename())
                .type(imageFile.getContentType())
                .imageData(ImageUtils.compressImage(imageFile.getBytes()))
                .build();
        return imageRepository.save(imageToSave);
    }

    @Override
    public Image updateImage(Integer id, MultipartFile imageFile) throws IOException {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("Image not found id: " + id));
        image.setName(imageFile.getOriginalFilename());
        image.setType(imageFile.getContentType());
        image.setImageData(imageFile.getBytes());

        return imageRepository.save(image);
    }

    @Override
    public Image getImageById(Integer id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("Image not found with id: " + id));

    }

    @Override
    public byte[] downloadImage(String imageName) {
        return imageRepository.findByName(imageName)
                .map(this::decompressImageOrThrow)
                .orElse(null);
    }

    private byte[] decompressImageOrThrow(Image image) {
        try {
            return ImageUtils.decompressImage(image.getImageData());
        } catch (DataFormatException | IOException exception) {
            throw new ContextedRuntimeException("Error downloading an image", exception)
                    .addContextValue("Image ID", image.getId())
                    .addContextValue("Image name", image.getName());
        }
    }

    @Override
    public void delete(Integer id) {
        imageRepository.deleteById(id);
    }
}

