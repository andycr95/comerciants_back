package com.andycaicedo.comerciants.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.andycaicedo.comerciants.entity.City;
import com.andycaicedo.comerciants.repository.CityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityService {


    private final CityRepository cityRepository;

    public List<City> getCitiesByDepartmentId(Long department_id) {
        try {
            return cityRepository.findByDepartmentId(department_id);
        } catch (Exception e) {
            throw new RuntimeException("Error consulting Cities", e);
        }
    }
}
