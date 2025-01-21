package com.andycaicedo.comerciants.service;

import java.util.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andycaicedo.comerciants.dto.comerciants.ComerciantDTO;
import com.andycaicedo.comerciants.dto.comerciants.ConsultComerciantDTO;
import com.andycaicedo.comerciants.dto.establishment.EstablishmentDTO;
import com.andycaicedo.comerciants.entity.Comerciant;
import com.andycaicedo.comerciants.entity.ComerciantRecord;
import com.andycaicedo.comerciants.entity.User;
import com.andycaicedo.comerciants.repository.ComerciantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComerciantService {

    private final JdbcTemplate jdbcTemplate;
    private final ComerciantRepository comerciantRepository;
    private final PdfService pdfService;
    private final EstablishmentService establishmentService;

    public Map<String, Object> getComerciants(ConsultComerciantDTO comerciantDTO) {
        try {
            SimpleJdbcCall simpleJdbcCall = createSimpleJdbcCall("consult", false);
                    simpleJdbcCall.returningResultSet("ReturnValue", (rs, rowNum) -> {
                        return ComerciantRecord.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .department(rs.getString("department"))
                        .city(rs.getString("city"))
                        .phone(rs.getString("phone"))
                        .email(rs.getString("email"))
                        .registration_date(rs.getString("registration_date"))
                        .status(rs.getString("status"))
                        .total_assets(rs.getString("total_assets") != null ? Double.parseDouble(rs.getString("total_assets")) : 0.0)
                        .number_of_employees(rs.getInt("number_of_employees"))
                        .number_of_establishments(rs.getInt("number_of_establishments"))
                        .build();
                });

            SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_name", comerciantDTO.getName())
                .addValue("p_city", comerciantDTO.getCityId())
                .addValue("p_registration_date", comerciantDTO.getRegistration_date())
                .addValue("p_status", comerciantDTO.getStatus())
                .addValue("p_page", comerciantDTO.getPage())
                    .addValue("p_records_by_page", comerciantDTO.getLimit());

            Map<String, Object> out = simpleJdbcCall.execute(in);

            List<ComerciantRecord> comerciantRecords = (List<ComerciantRecord>) out.get("ReturnValue");
            Map<String, Object> pagination = getTotalComerciants(comerciantDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("comerciants", comerciantRecords);
            response.put("totalRecords", pagination.get("totalRecords"));
            response.put("totalPages", pagination.get("totalPages"));
            response.put("currentPage", comerciantDTO.getPage());

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error inn report comerciant", e);
        }
    }

    public Map<String, Object> getTotalComerciants(ConsultComerciantDTO comerciantDTO) {
        try {
            SimpleJdbcCall simpleJdbcCall = createSimpleJdbcCall("total_comerciants", false);
            SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_name", comerciantDTO.getName())
                .addValue("p_city", comerciantDTO.getCityId())
                .addValue("p_registration_date", comerciantDTO.getRegistration_date())
                .addValue("p_status", comerciantDTO.getStatus());

            Map<String, Object> out = simpleJdbcCall.execute(in);

            BigDecimal totalRecordsBigDecimal = (BigDecimal) out.get("return");
            int totalRecords = totalRecordsBigDecimal.intValue();
            int totalPages = (int) Math.ceil(totalRecords / comerciantDTO.getLimit().doubleValue());

            Map<String, Object> response = new HashMap<>();
            response.put("totalRecords", totalRecords);
            response.put("totalPages", totalPages);

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error inn report comerciant", e);
        }
    }
    
    public Comerciant getComerciantById(Long id) {
        try {
            return comerciantRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            throw new RuntimeException("Error consulting comerciant", e);
        }
    }
    
    public List<ComerciantRecord> getReportComerciant() {
        try {
            SimpleJdbcCall simpleJdbcCall = createSimpleJdbcCall("report_comerciants", false);
            simpleJdbcCall.returningResultSet("ReturnValue", (rs, rowNum) -> {
                ComerciantRecord comerciant = ComerciantRecord.builder()
                    .name(rs.getString("name"))
                    .department(rs.getString("department"))
                    .city(rs.getString("city"))
                    .phone(rs.getString("phone"))
                    .email(rs.getString("email"))
                    .registration_date(rs.getString("registration_date"))
                    .status(rs.getString("status"))
                    .number_of_establishments(rs.getInt("number_of_establishments"))
                    .total_assets(rs.getDouble("total_assets"))
                    .number_of_employees(rs.getInt("number_of_employees"))
                    .build();
                return comerciant;
            });

            SqlParameterSource in = new MapSqlParameterSource();
            Map<String, Object> out = simpleJdbcCall.execute(in);
            return (List<ComerciantRecord>) out.get("ReturnValue");
        } catch (Exception e) {
            throw new RuntimeException("Error en el reporte de comerciant", e);
        }
    }
    
    @Transactional
    public Map<String, Object> createComerciant(ComerciantDTO comerciantDTO) {
        try {
            SimpleJdbcCall simpleJdbcCall = createSimpleJdbcCall("create_comerciant", true);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();

            SqlParameterSource in = createSqlParameterSource(comerciantDTO, user.getId(), null);
            Map<String, Object> out = simpleJdbcCall.execute(in);
            Number comerciantId = (Number) out.get("P_ID");
            for (EstablishmentDTO establishment : comerciantDTO.getEstablishments()) {
                establishmentService.createEstablishment(establishment, comerciantId, user.getId());
            }
            return out;
        } catch (Exception e) {
            System.err.println("Error creating comerciant: " + e.getMessage());
            throw new RuntimeException("Error creating comerciant", e);
        }
    }

    @Transactional
    public String updateComerciant(ComerciantDTO comerciantDTO, Long id) {
        try {
            SimpleJdbcCall simpleJdbcCall = createSimpleJdbcCall("update_comerciant", true);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();

            SqlParameterSource in = createSqlParameterSource(comerciantDTO, user.getId(), id);
            simpleJdbcCall.execute(in);
            return "User updated";
        } catch (Exception e) {
            System.err.println("Error updating comerciant: " + e.getMessage());
            throw new RuntimeException("Error updating comerciant", e);
        }
    }

    @Transactional
    public Map<String, Object> updateComerciantStatus(Long id, String status) {
        try {
            SimpleJdbcCall simpleJdbcCall = createSimpleJdbcCall("update_comerciant_status", true);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id", id)
                .addValue("p_status", status)
                .addValue("p_updated_by", user.getId())
                .addValue("p_error_code", null)
                .addValue("p_error_message", null);
                
            return simpleJdbcCall.execute(in);
        } catch (Exception e) {
            System.err.println("Error actualizando el estado del comerciant: " + e.getMessage());
            throw new RuntimeException("Error actualizando el estado del comerciant", e);
        }
    }

    @Transactional
    public Map<String, Object> deleteComerciant(Long id) {
        try {

            SimpleJdbcCall simpleJdbcCallEst = createSimpleJdbcCall("delete_establishments_comerciant", true);
            SqlParameterSource inEst = new MapSqlParameterSource()
                .addValue("p_commerciant_id", id)
                .addValue("p_error_code", null)
                    .addValue("p_error_message", null);
                
            SimpleJdbcCall simpleJdbcCall = createSimpleJdbcCall("delete_comerciant", true);
            SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id", id)
                .addValue("p_error_code", null)
                .addValue("p_error_message", null);
                
            simpleJdbcCallEst.execute(inEst);
            
            return simpleJdbcCall.execute(in);
        } catch (Exception e) {
            System.err.println("Error deleting comerciant: " + e.getMessage());
            throw new RuntimeException("Error deleting comerciant", e);
        }
    }

    private SqlParameterSource createSqlParameterSource(ComerciantDTO comerciantDTO, Long userId, Long id) {
        return new MapSqlParameterSource()
                .addValue(id != null ? "p_id" : "", id)
                .addValue("p_name", comerciantDTO.getName())
                .addValue("p_department_id", comerciantDTO.getDepartmentId())
                .addValue("p_city_id", comerciantDTO.getCityId())
                .addValue("p_phone", comerciantDTO.getPhone() != null ? comerciantDTO.getPhone() : null)
                .addValue("p_email", comerciantDTO.getEmail() != null ? comerciantDTO.getEmail() : null)
                .addValue("p_registration_date", new java.sql.Date(System.currentTimeMillis()))
                .addValue("p_status", comerciantDTO.getStatus())
                .addValue(id == null ? "p_created_by" : "p_updated_by", userId)
                .addValue("p_error_code", null)
                .addValue("p_error_message", null);
    }
    
    private SimpleJdbcCall createSimpleJdbcCall(String name, boolean isProcedure) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("pkg_comerciants");

        if (isProcedure) {
            return simpleJdbcCall.withProcedureName(name).declareParameters(
                    new SqlOutParameter("p_error_code", Types.INTEGER),
                    new SqlOutParameter("p_error_message", Types.VARCHAR));
        } else {
            return simpleJdbcCall.withFunctionName(name);
        }
    }
        
     public byte[] generatePdfComerciant(Long id) {
        Comerciant comerciant = comerciantRepository.findById(id).orElseThrow(() -> new RuntimeException("Comerciant no encontrado con id: " + id));
        try {
            return pdfService.generatePDF(comerciant);
        } catch (IOException e) {
            throw new RuntimeException("Error al generar el PDF para el comerciant con id: " + id, e);
        }
    }
    
}
