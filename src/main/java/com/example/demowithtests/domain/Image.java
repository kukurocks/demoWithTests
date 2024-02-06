package com.example.demowithtests.domain;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String type;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] imageData;

    @ToString.Exclude
    @OneToOne(mappedBy = "image")
    private Passport passport;
}