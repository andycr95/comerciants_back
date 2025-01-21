package com.andycaicedo.comerciants.entity;


import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "DEPARTMENTS")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEPARTMENTS")
    @SequenceGenerator(name = "SEQ_DEPARTMENTS", sequenceName = "SEQ_DEPARTMENTS", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;
}