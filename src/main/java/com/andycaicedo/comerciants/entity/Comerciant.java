package com.andycaicedo.comerciants.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "COMERCIANTS")
public class Comerciant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMERCIANTS")
    @SequenceGenerator(name = "SEQ_COMERCIANTS", sequenceName = "SEQ_COMERCIANTS", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "CITY_ID", nullable = false)
    private City city;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "REGISTRATION_DATE", nullable = false)
    private Date registrationDate;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "CREATION_DATE", nullable = false)
    private Date creationDate;

    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy;

    @Column(name = "UPDATE_DATE", nullable = false)
    private Date updateDate;

    @Column(name = "UPDATED_BY", nullable = false)
    private String updatedBy;

    @OneToMany(mappedBy = "commerciantId")
    private List<Establishment> establishments;

}
