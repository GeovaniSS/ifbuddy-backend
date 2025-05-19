package br.com.ifbuddy.enums.converters;

import br.com.ifbuddy.enums.TipoCaracteristica;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoCaracteristicaConverter implements AttributeConverter<TipoCaracteristica, String> {

  @Override
  public String convertToDatabaseColumn(TipoCaracteristica tipoCaracteristica) {
    return tipoCaracteristica != null ? tipoCaracteristica.getKey() : null;
  }

  @Override
  public TipoCaracteristica convertToEntityAttribute(String dbData) {
    return dbData != null ? TipoCaracteristica.fromKey(dbData) : null;
  }
}