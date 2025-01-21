package com.andycaicedo.comerciants.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.andycaicedo.comerciants.dto.auth.AuthResponse;
import com.andycaicedo.comerciants.dto.auth.LoginDTO;
import com.andycaicedo.comerciants.entity.User;
import com.andycaicedo.comerciants.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + loginDTO.getEmail()));        
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();
    }
    
}
