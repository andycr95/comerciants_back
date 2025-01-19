package com.andycaicedo.comerciants.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "ROLES")
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROLES")
    @SequenceGenerator(name = "SEQ_ROLES", sequenceName = "SEQ_ROLES", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ROLE_NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

}
