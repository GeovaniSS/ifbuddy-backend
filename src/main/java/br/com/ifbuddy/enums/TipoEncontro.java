package br.com.ifbuddy.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoEncontro {
  ONLINE("O", "Online"),
  PRESENCIAL("P", "Presencial");

  private final String key;
  private final String descricao;

  TipoEncontro(String key, String descricao) {
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

  public static TipoEncontro fromKey(String key) {
    for (TipoEncontro tipoEncontro : values()) {
      if (tipoEncontro.getKey().equalsIgnoreCase(key)) {
        return tipoEncontro;
      }
    }
    throw new IllegalArgumentException("Tipo Encontro inválido: " + key);
  }
}
