package com.andycaicedo.comerciants.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.andycaicedo.comerciants.dto.comerciants.ComerciantDTO;
import com.andycaicedo.comerciants.dto.comerciants.ConsultComerciantDTO;
import com.andycaicedo.comerciants.entity.Department;
import com.andycaicedo.comerciants.service.ComerciantService;
import com.andycaicedo.comerciants.service.DepartmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin("*")
@RequestMapping("departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping()
    public ResponseEntity<List<Department>> getDepartments()  {
        try {
            return ResponseEntity.ok(departmentService.getDepartments());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    
}
