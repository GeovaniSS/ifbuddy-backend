package br.com.ifbuddy.enums.converters;

import br.com.ifbuddy.enums.Turno;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TurnoConverter implements AttributeConverter<Turno, String> {

  @Override
  public String convertToDatabaseColumn(Turno turno) {
    return turno != null ? turno.getKey() : null;
  }

  @Override
  public Turno convertToEntityAttribute(String dbData) {
    return dbData != null ? Turno.fromKey(dbData) : null;
  }
}