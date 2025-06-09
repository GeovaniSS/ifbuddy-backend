package br.com.ifbuddy.rest.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class CriarDisponibilidadeDTO {
  private Integer diaSemana;
  private String textoDiaSemana;
  private Boolean ativo;
  private Horarios horarios;
  private Encontros encontros;
}
