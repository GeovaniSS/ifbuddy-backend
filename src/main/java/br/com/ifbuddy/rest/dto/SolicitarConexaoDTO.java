package br.com.ifbuddy.rest.dto;

import lombok.Data;

@Data
public class SolicitarConexaoDTO {
  private Long solicitanteId;
  private Long solicitadoId;
}
