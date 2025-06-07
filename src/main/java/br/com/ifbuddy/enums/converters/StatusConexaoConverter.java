package br.com.ifbuddy.enums.converters;

import br.com.ifbuddy.enums.StatusConexao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConexaoConverter implements AttributeConverter<StatusConexao, String> {

  @Override
  public String convertToDatabaseColumn(StatusConexao statusConexao) {
    return statusConexao != null ? statusConexao.getKey() : null;
  }

  @Override
  public StatusConexao convertToEntityAttribute(String dbData) {
    return dbData != null ? StatusConexao.fromKey(dbData) : null;
  }
}