package br.com.ifbuddy.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoTCC {
  MONOGRAFIA("M", "Monografia"),
  ARTIGO("A", "Artigo Científico"),
  PROJETO("P", "Projeto Técnico");

  private final String key;
  private final String descricao;

  TipoTCC(String key, String descricao) {
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

  public static TipoTCC fromKey(String key) {
    for (TipoTCC tipoTCC : values()) {
      if (tipoTCC.getKey().equalsIgnoreCase(key)) {
        return tipoTCC;
      }
    }
    throw new IllegalArgumentException("Tipo TCC inválido: " + key);
  }
}
