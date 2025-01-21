package com.andycaicedo.comerciants.dto.comerciants;

import java.util.List;
import java.util.Map;

import com.andycaicedo.comerciants.dto.establishment.EstablishmentDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComerciantDTO {
    @NotBlank(message = "name is required")
    private final String name;
    
    @NotBlank(message = "department id is required")
    @Positive(message = "department id must be a integer number")
    private final Long departmentId;
    
    @NotBlank(message = "city id is required")
    @Positive(message = "city id must be a integer number")
    private final Long cityId;

    private final String phone;

    private final Long id;

    private final String email;

    @NotBlank(message = "registration_date is required")
    private final String registration_date;

    @NotBlank(message = "registration_date is required")
    private final String status;

    private final List<EstablishmentDTO> establishments;

    // Constructor
    public ComerciantDTO(Long id, String name, Long departmentId, Long cityId, String phone, String email, String registration_date, String status, List<EstablishmentDTO> establishments) {
        this.name = name;
        this.departmentId = departmentId;
        this.cityId = cityId;
        this.phone = phone;
        this.email = email;
        this.id = id;
        this.registration_date = registration_date;
        this.status = status;
        this.establishments = establishments;
    }
}
