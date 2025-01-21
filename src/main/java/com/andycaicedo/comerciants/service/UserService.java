package com.andycaicedo.comerciants.service;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andycaicedo.comerciants.dto.user.CreateDTO;
import com.andycaicedo.comerciants.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public Map<String, Object> save(CreateDTO createDTO) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();

            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName("pkg_users")
                    .withProcedureName("create_user");

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("p_name", createDTO.getName())
                    .addValue("p_password", passwordEncoder.encode(createDTO.getPassword()))
                    .addValue("p_email", createDTO.getEmail())
                    .addValue("p_role_id", createDTO.getRoleId())
                    .addValue("p_created_by", user.getId())
                    .addValue("p_error_code", 0)
                    .addValue("p_error_message", "");
                    System.out.println(in);
            return simpleJdbcCall.execute(in);
        } catch (Exception e) {
            throw new RuntimeException("Error creating user", e);
        }
    }
    
}
