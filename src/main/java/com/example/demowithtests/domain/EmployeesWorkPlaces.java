package com.example.demowithtests.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "users_work_places")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class EmployeesWorkPlaces {

    @EmbeddedId
    EmployeesWorkPlacesKey employeesWorkPlacesKey = new EmployeesWorkPlacesKey();

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @MapsId("employeeId")
    @ToString.Exclude
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "work_places_id")
    @MapsId("wpId")
    @ToString.Exclude
    @JsonIgnore
    private WorkPlace workPlace;

    private Boolean isActive = Boolean.TRUE;

}
