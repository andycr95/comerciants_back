package com.andycaicedo.comerciants.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.andycaicedo.comerciants.entity.City;
import com.andycaicedo.comerciants.service.CityService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("cities")
public class CityController {

    private final CityService cityService;

    @GetMapping("department/{department_id}")
    public ResponseEntity<List<City>> getCitiesByDepartmentId(@PathVariable Long department_id) {
        try {
            return ResponseEntity.ok(cityService.getCitiesByDepartmentId(department_id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    
}
