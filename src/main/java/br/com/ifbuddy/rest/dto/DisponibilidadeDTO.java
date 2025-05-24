package br.com.ifbuddy.rest.dto;

import lombok.Data;

@Data
public class DisponibilidadeDTO {
  private Integer diaSemana;
  private String tipoEncontro;
  private String turnos;
}
