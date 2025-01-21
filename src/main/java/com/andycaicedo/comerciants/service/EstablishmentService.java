package com.andycaicedo.comerciants.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andycaicedo.comerciants.dto.establishment.EstablishmentDTO;
import com.andycaicedo.comerciants.entity.Establishment;
import com.andycaicedo.comerciants.repository.EstablishRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstablishmentService {

    private final EstablishRepository establishmentRepository;

    @Transactional
    public Establishment createEstablishment(EstablishmentDTO establishmentDTO, Number comerciantId, Long userId) {
        try {
            Establishment establishment = Establishment.builder()
            .commerciantId(establishmentDTO.getComerciantId() != null ? establishmentDTO.getComerciantId() : comerciantId.intValue())
            .name(establishmentDTO.getName())
            .revenue(establishmentDTO.getRevenue())
            .employee_count(establishmentDTO.getEmployee_count())
            .createdBy(userId.toString())
            .build();
            return establishmentRepository.save(establishment);
        } catch (Exception e) {
            System.err.println("Error creating comerciant: " + e.getMessage());
            throw new RuntimeException("Error creating comerciant", e);
        }
    }
    
}
