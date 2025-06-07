package br.com.ifbuddy.rest.dto;

import java.util.List;

import lombok.Data;

@Data
public class FiltrosDTO {
  private Long estudanteId;
  private Long cursoId;
  private String turno;
  private Integer semestre;
  private String tipoTCC;
  private List<Long> temasIds;
  private List<Long> pontosFortesIds;
}
