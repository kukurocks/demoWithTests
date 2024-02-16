package com.example.demowithtests.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
public class PassportEvent {

    public PassportEvent(Action action, Integer employeeId) {
        this.action = action;
        this.timestamp = LocalDateTime.now();
        this.employeeId = employeeId;
    }

    public PassportEvent() {

    }


   @Enumerated(value = EnumType.STRING)
    private Action action;
    private LocalDateTime timestamp;
    private Integer employeeId;


}