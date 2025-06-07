package br.com.ifbuddy.enums.converters;

import br.com.ifbuddy.enums.DiaSemana;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DiaSemanaConverter implements AttributeConverter<DiaSemana, Integer> {

  @Override
  public Integer convertToDatabaseColumn(DiaSemana diaSemana) {
    return diaSemana != null ? diaSemana.getKey() : null;
  }

  @Override
  public DiaSemana convertToEntityAttribute(Integer dbData) {
    return dbData != null ? DiaSemana.fromKey(dbData) : null;
  }
}