package com.andycaicedo.comerciants.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateDTO {
    public CreateDTO(String name, Long roleId, String email, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.roleId = roleId;
    }


    @NotBlank(message = "name is required")
    private String name;
    
    @NotBlank(message = "department id is required")
    @Positive(message = "department id must be a integer number")
    private Long roleId;


    @NotBlank(message = "email id is required")
    private String email;

    @NotBlank(message = "password id is required")
    private String password;
}