package com.andycaicedo.comerciants.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andycaicedo.comerciants.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
 }