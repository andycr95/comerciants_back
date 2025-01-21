package com.andycaicedo.comerciants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andycaicedo.comerciants.entity.Comerciant;

@Repository
public interface ComerciantRepository extends JpaRepository<Comerciant, Long>{}
