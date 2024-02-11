package com.example.demowithtests.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class WorkPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer quantity = 1;
    private Boolean isDeleted = Boolean.FALSE;

    @OneToMany(mappedBy = "workPlace")
    @ToString.Exclude
    private Set<EmployeesWorkPlaces> employeesWorkPlaces = new HashSet<>();

    private Integer freePlacesCount = quantity;
    private Boolean hasFreePlaces = Boolean.TRUE;

}

