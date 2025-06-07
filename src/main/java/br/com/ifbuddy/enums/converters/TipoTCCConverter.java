package br.com.ifbuddy.enums.converters;

import br.com.ifbuddy.enums.TipoTCC;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoTCCConverter implements AttributeConverter<TipoTCC, String> {

  @Override
  public String convertToDatabaseColumn(TipoTCC tipoTCC) {
    return tipoTCC != null ? tipoTCC.getKey() : null;
  }

  @Override
  public TipoTCC convertToEntityAttribute(String dbData) {
    return dbData != null ? TipoTCC.fromKey(dbData) : null;
  }
}