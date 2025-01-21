package com.andycaicedo.comerciants.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.andycaicedo.comerciants.entity.Department;
import com.andycaicedo.comerciants.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {


    private final DepartmentRepository departmentRepository;

    public List<Department> getDepartments() {
        try {
            return departmentRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error consulting departments", e);
        }
    }
}
