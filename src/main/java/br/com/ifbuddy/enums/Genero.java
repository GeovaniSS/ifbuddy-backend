package br.com.ifbuddy.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Genero {
  MASCULINO("M", "Masculino"),
  FEMININO("F", "Feminino"),
  OUTRO("O", "Outro");

  private final String key;
  private final String descricao;

  Genero(String key, String descricao) {
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

  public static Genero fromKey(String key) {
    for (Genero genero : values()) {
      if (genero.getKey().equalsIgnoreCase(key)) {
        return genero;
      }
    }
    throw new IllegalArgumentException("Gênero inválido: " + key);
  }
}
