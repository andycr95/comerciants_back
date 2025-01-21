package com.andycaicedo.comerciants.entity;

import jakarta.persistence.*;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "CITIES")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CITIES")
    @SequenceGenerator(name = "SEQ_CITIES", sequenceName = "SEQ_CITIES", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DEPARTMENT_ID", nullable = false)
    private Long departmentId;
}