package com.andycaicedo.comerciants.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.andycaicedo.comerciants.entity.ComerciantRecord;

@Service
public class CsvService {


    public byte[] generateCSVFile(List<ComerciantRecord> comerciantRecord) throws IOException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ICsvBeanWriter csvWriter = new CsvBeanWriter(new OutputStreamWriter(outputStream), CsvPreference.STANDARD_PREFERENCE);
            String[] nameMapping = {"id", "name", "department", "city", "phone", "email", "registration_date", "status", "total_assets", "number_of_employees", "number_of_establishments"};

            csvWriter.writeHeader(nameMapping);
            if (!comerciantRecord.isEmpty()) {
                for (ComerciantRecord record : comerciantRecord) {
                    csvWriter.write(record, nameMapping);
                }
            }
            csvWriter.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error en el reporte de comerciant", e);
        }
    }
}
