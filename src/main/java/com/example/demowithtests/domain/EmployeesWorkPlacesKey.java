package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesWorkPlacesKey implements Serializable {

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name="work_places_id")
    private Integer wpId;

}
