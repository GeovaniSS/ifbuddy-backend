package br.com.ifbuddy.rest.dto;

import lombok.Data;

@Data
public class CriarDisponibilidadeDTO {
  private Integer diaSemana;
  private String textoDiaSemana;
  private Boolean ativo;
  private Horarios horarios;
  private Encontros encontros;
}
