package br.com.ifbuddy.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoCaracteristica {
  FORTE("F", "Forte"),
  FRACA("D", "Fraca");

  private final String key;
  private final String descricao;

  TipoCaracteristica(String key, String descricao) {
    this.key = key;
    this.descricao = descricao;
  }

  @JsonCreator
  public String getKey() {
    return key;
  }

  @JsonValue
  public String getDescricao() {
    return descricao;
  }

  public static TipoCaracteristica fromKey(String key) {
    for (TipoCaracteristica tipoCaracteristica : values()) {
      if (tipoCaracteristica.getKey().equalsIgnoreCase(key)) {
        return tipoCaracteristica;
      }
    }
    throw new IllegalArgumentException("Tipo Característica inválido: " + key);
  }
}
