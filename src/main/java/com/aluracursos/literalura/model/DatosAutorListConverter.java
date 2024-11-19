package com.aluracursos.literalura.model;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Converter
public class DatosAutorListConverter  implements AttributeConverter<List<DatosAutor>, String> {

        private static final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(List<DatosAutor> datosAutorList) {
            try {
                return objectMapper.writeValueAsString(datosAutorList);
            } catch (Exception e) {
                throw new RuntimeException("Error al convertir List<DatosAutor> a JSON", e);
            }
        }

        @Override
        public List<DatosAutor> convertToEntityAttribute(String datosAutorJson) {
            try {
                return objectMapper.readValue(datosAutorJson, new TypeReference<List<DatosAutor>>() {});
            } catch (IOException e) {
                throw new RuntimeException("Error al convertir JSON a List<DatosAutor>", e);
            }
        }
    }


