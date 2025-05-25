package br.com.ifbuddy.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TurnoDia {
  MANHA("M", "Manhã"),
  TARDE("T", "Tarde"),
  NOITE("N", "Noite");

  private final String key;
  private final String descricao;

  TurnoDia(String key, String descricao) {
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

  public static TurnoDia fromKey(String key) {
    for (TurnoDia turno : values()) {
      if (turno.getKey().equalsIgnoreCase(key)) {
        return turno;
      }
    }
    throw new IllegalArgumentException("Turno Dia inválido: " + key);
  }
}
