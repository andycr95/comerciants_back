package com.andycaicedo.comerciants.dto.auth;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class LoginDTO {

    
    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }


    @NotBlank(message = "email is required")
    private String email;  


    @NotNull(message = "password is required")
    private String password;

}
