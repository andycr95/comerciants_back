package com.andycaicedo.comerciants.entity;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "ESTABLISHMENTS")
public class Establishment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESTABLISHMENTS")
    @SequenceGenerator(name = "SEQ_ESTABLISHMENTS", sequenceName = "SEQ_ESTABLISHMENTS", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "COMMERCIANT_ID", insertable = true, updatable = true)
    private Long commerciantId;

    @Column(name = "REVENUE", nullable = false)
    private Double revenue;

    @Column(name = "EMPLOYEE_COUNT")
    private Integer employee_count;

    @Column(name = "CREATION_DATE", nullable = true)
    private Date creationDate;

    @Column(name = "CREATED_BY", nullable = true)
    private String createdBy;

    @Column(name = "UPDATE_DATE", nullable = true)
    private Date updateDate;

    @Column(name = "UPDATED_BY", nullable = true)
    private String updatedBy;
}
