package com.andycaicedo.comerciants.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.andycaicedo.comerciants.dto.auth.AuthResponse;
import com.andycaicedo.comerciants.dto.auth.LoginDTO;
import com.andycaicedo.comerciants.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }
    
    
}
