package com.andycaicedo.comerciants.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.andycaicedo.comerciants.dto.auth.AuthResponse;
import com.andycaicedo.comerciants.dto.auth.LoginDTO;
import com.andycaicedo.comerciants.dto.user.CreateDTO;
import com.andycaicedo.comerciants.entity.User;
import com.andycaicedo.comerciants.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse save(CreateDTO createDTO) {
        System.out.println("email " + createDTO.getEmail());
        System.out.println("pass " + createDTO.getPassword());
        System.out.println("rol " + createDTO.getRoleId());
        System.out.println("name " + createDTO.getName());
        String pass = passwordEncoder.encode(createDTO.getPassword());
        System.out.println(pass);

        User user = User.builder()
            .name(createDTO.getName())
            .email(createDTO.getEmail())
            .password(pass)
            .role_id(createDTO.getRoleId())
            .build();

        // userRepository.save(user);

        return AuthResponse.builder()
            .token("jwtService.getToken(user)")
            .build();
    }
    
}
