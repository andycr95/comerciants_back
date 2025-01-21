package com.andycaicedo.comerciants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andycaicedo.comerciants.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{}
