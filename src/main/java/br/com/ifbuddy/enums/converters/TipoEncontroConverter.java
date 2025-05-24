package br.com.ifbuddy.enums.converters;

import br.com.ifbuddy.enums.TipoEncontro;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoEncontroConverter implements AttributeConverter<TipoEncontro, String> {

  @Override
  public String convertToDatabaseColumn(TipoEncontro tipoEncontro) {
    return tipoEncontro != null ? tipoEncontro.getKey() : null;
  }

  @Override
  public TipoEncontro convertToEntityAttribute(String dbData) {
    return dbData != null ? TipoEncontro.fromKey(dbData) : null;
  }
}