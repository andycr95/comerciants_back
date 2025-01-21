package com.andycaicedo.comerciants.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ComerciantRecord {
    
    private Long id;
    private String name;
    private String department;
    private String city;
    private String email;
    private String phone;
    private String registration_date;
    private String status;
    private Double total_assets;
    private int number_of_employees;
}
