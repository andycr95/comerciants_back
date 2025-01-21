package com.andycaicedo.comerciants.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andycaicedo.comerciants.entity.City;


@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByDepartmentId(Long department_id);
}
