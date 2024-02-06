package com.example.demowithtests.util.config.mapstruct;

import com.example.demowithtests.domain.Image;
import com.example.demowithtests.dto.ImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    Image toImage(ImageDto dto);

    ImageDto toDto(Image image);

}