package com.andycaicedo.comerciants.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.andycaicedo.comerciants.dto.comerciants.ComerciantDTO;
import com.andycaicedo.comerciants.dto.comerciants.ConsultComerciantDTO;
import com.andycaicedo.comerciants.entity.Comerciant;
import com.andycaicedo.comerciants.entity.ComerciantRecord;
import com.andycaicedo.comerciants.service.ComerciantService;
import com.andycaicedo.comerciants.service.CsvService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin("*")
@RequestMapping("comerciants")
public class ComerciantController {

    private final ComerciantService comerciantService;
    private final CsvService csvService;

    @GetMapping()
    public ResponseEntity<Map<String, Object>> gerComerciants(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Long cityId,
        @RequestParam(required = false) String registration_date,
        @RequestParam(required = false) String status,
        @RequestParam(defaultValue = "1") Number page,
        @RequestParam(defaultValue = "5") Number limit) throws Exception {
        try {
            ConsultComerciantDTO comerciantDTO = ConsultComerciantDTO.builder()
                .cityId(cityId)
                .registration_date(registration_date)
                .status(status)
                .page(page)
                .limit(limit)
                .build();
            return ResponseEntity.ok(comerciantService.getComerciants(comerciantDTO));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Comerciant> gerComerciantById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(comerciantService.getComerciantById(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("report")
    public ResponseEntity<byte[]> getReportComerciantCsv() {
        try {
            List<ComerciantRecord> comerciants = comerciantService.getReportComerciant();
            byte[] csvContent = csvService.generateCSVFile(comerciants);
            return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=report_comerciants.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csvContent);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createComerciant(@RequestBody ComerciantDTO comerciantDTO) {
        try {
            return ResponseEntity.ok(comerciantService.createComerciant(comerciantDTO));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComerciant(@RequestBody ComerciantDTO comerciantDTO, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(comerciantService.updateComerciant(comerciantDTO, id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteComerciant(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(comerciantService.deleteComerciant(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> changeStatusComerciant(@PathVariable Long id,
            @RequestBody String status) {
        try {
            return ResponseEntity.ok(comerciantService.updateComerciantStatus(id, status.replaceAll("\"", "")));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generatePdfComerciant(@PathVariable Long id) {
        try {
            byte[] zipContent = comerciantService.generatePdfComerciant(id);
            return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=comerciant_"+id+".zip")
            .body(zipContent);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    
}
