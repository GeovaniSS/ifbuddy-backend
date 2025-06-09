package br.com.ifbuddy.rest.dto;

import br.com.ifbuddy.enums.StatusConexao;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class ConexaoDTO {
  private Long conexaoId;
  private ConexaoUsuarioDTO parceiro;
  private StatusConexao status;
  private String dataSolicitacao;
  private String dataAceite;
  private String dataEncerramento;
  private Boolean ativo;
}
