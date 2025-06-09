package br.com.ifbuddy.rest.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class DisponibilidadeDTO {
  private Integer diaSemana;
  private String textoDiaSemana;
  private String encontros;
  private String horarios;
}
