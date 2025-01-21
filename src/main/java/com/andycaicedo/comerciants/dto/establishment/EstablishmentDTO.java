package com.andycaicedo.comerciants.dto.establishment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class EstablishmentDTO {
    @NotBlank(message = "name is required")
    private final String name;
    
    private final Long comerciantId;

    private final Double revenue;

    private final int employee_count;

    // Constructor
    public EstablishmentDTO(String name, Long comerciant_id, Double revenue, int employee_count) {
        this.name = name;
        this.comerciantId = comerciant_id;
        this.revenue = revenue;
        this.employee_count = employee_count;
    }
}
