package com.example.demowithtests.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = "employee")
@Entity
@Table(name = "passports")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String number;

    @Column(unique = true)
    private String series;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    private LocalDateTime expireDate;

    @Builder.Default
    private Boolean isHanded = Boolean.FALSE;

    @ToString.Exclude
    @OneToOne(mappedBy = "passport")
    @JsonIgnore
    private Employee employee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    @JsonIgnore
    private Image image;

    @Embedded
    PassportEvent passportEvent;


}