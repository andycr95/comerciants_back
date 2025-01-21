package com.andycaicedo.comerciants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andycaicedo.comerciants.entity.Establishment;
import java.util.List;


@Repository
public interface EstablishRepository extends JpaRepository<Establishment, Long> {
    List<Establishment> findByCommerciantId(Long commerciantId);
}
