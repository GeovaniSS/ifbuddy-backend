package br.com.ifbuddy.enums.converters;

import br.com.ifbuddy.enums.Genero;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GeneroConverter implements AttributeConverter<Genero, String> {

    @Override
    public String convertToDatabaseColumn(Genero genero) {
        return genero != null ? genero.getKey() : null;
    }

    @Override
    public Genero convertToEntityAttribute(String dbData) {
        return dbData != null ? Genero.fromKey(dbData) : null;
    }
}