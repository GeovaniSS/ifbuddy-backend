package br.com.ifbuddy.rest.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class SolicitarConexaoDTO {
  private Long solicitanteId;
  private Long solicitadoId;
}
