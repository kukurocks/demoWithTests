package com.example.demowithtests.domain;

import com.example.demowithtests.util.annotation.entity.Name;
import com.example.demowithtests.util.annotation.entity.ToLowerCase;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Name
    private String name;

    private String email;

    @ToLowerCase
    private String country;
    private Boolean deleted;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Set<Address> addresses = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", deleted=" + deleted +
                ", addresses=" + addresses +
                ", gender=" + gender +
                '}';
    }
}
