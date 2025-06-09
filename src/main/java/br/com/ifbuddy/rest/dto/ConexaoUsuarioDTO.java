package br.com.ifbuddy.rest.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class ConexaoUsuarioDTO {
  private Long id;
  private String foto;
  private String nome;
  private String email;
  private String telefone;
}
