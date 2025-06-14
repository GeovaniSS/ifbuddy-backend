package br.com.ifbuddy.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Turno {
  MATUTINO("M", "Matutino"),
  VESPERTINO("V", "Vespertino"),
  NOTURNO("N", "Noturno");

  private final String key;
  private final String descricao;

  Turno(String key, String descricao) {
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

  public static Turno fromKey(String key) {
    for (Turno turno : values()) {
      if (turno.getKey().equalsIgnoreCase(key)) {
        return turno;
      }
    }
    throw new IllegalArgumentException("Turno inválido: " + key);
  }
}
