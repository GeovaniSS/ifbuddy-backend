package br.com.ifbuddy.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusConexao {
  PENDENTE("P", "Pendente"),
  ACEITA("A", "Aceita"),
  RECUSADA("R", "Recusada"),
  CANCELADA("C", "Cancelada");

  private final String key;
  private final String descricao;

  StatusConexao(String key, String descricao) {
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

  public static StatusConexao fromKey(String key) {
    for (StatusConexao statusConexao : values()) {
      if (statusConexao.getKey().equalsIgnoreCase(key)) {
        return statusConexao;
      }
    }
    throw new IllegalArgumentException("Status Conexão inválido: " + key);
  }
}
