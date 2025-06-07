package br.com.ifbuddy.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Horario {
  MANHA("M", "Manhã"),
  TARDE("T", "Tarde"),
  NOITE("N", "Noite");

  private final String key;
  private final String descricao;

  Horario(String key, String descricao) {
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

  public static Horario fromKey(String key) {
    for (Horario horario : values()) {
      if (horario.getKey().equalsIgnoreCase(key)) {
        return horario;
      }
    }
    throw new IllegalArgumentException("Horário inválido: " + key);
  }
}
