package com.andycaicedo.comerciants.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andycaicedo.comerciants.dto.user.CreateDTO;
import com.andycaicedo.comerciants.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @PostMapping
    public ResponseEntity< Map<String, Object>> save(@RequestBody CreateDTO createDTO) {
        try {
            return ResponseEntity.ok(userService.save(createDTO));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
}
