package com.andycaicedo.comerciants.dto.comerciants;

import jakarta.validation.constraints.Positive;
import lombok.*;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ConsultComerciantDTO {
    
    public ConsultComerciantDTO(String name, Long cityId, String registration_date, String status, Number page, Number limit) {
        this.name = name;
        this.cityId = cityId;
        this.status = status;
        this.registration_date = registration_date;
        this.page = page;
        this.limit = limit;
    }

    private String name;
    private String registration_date;
    private String status;
    @Positive(message = "city id must be a integer number")
    private Long cityId;
    @Positive(message = "page must be a integer number")
    private Number page;
    @Positive(message = "limit must be a integer number")
    private Number limit;
}
