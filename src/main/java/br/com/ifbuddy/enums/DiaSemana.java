package br.com.ifbuddy.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DiaSemana {
  SEGUNDA(1, "Segunda"),
  TERCA(2, "Terça"),
  QUARTA(3, "Quarta"),
  QUINTA(4, "Quinta"),
  SEXTA(5, "Sexta"),
  SABADO(6, "Sábado"),
  DOMINGO(7, "Domingo");

  private final Integer key;
  private final String descricao;

  DiaSemana(Integer key, String descricao) {
    this.key = key;
    this.descricao = descricao;
  }

  @JsonCreator
  public Integer getKey() {
    return key;
  }

  @JsonValue
  public String getDescricao() {
    return descricao;
  }

  public static DiaSemana fromKey(Integer key) {
    for (DiaSemana diaSemana : values()) {
      if (diaSemana.getKey() == key) {
        return diaSemana;
      }
    }
    throw new IllegalArgumentException("Dia da semana inválido: " + key);
  }
}
