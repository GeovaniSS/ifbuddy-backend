package br.com.ifbuddy.rest.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RegisterForReflection
public class UsuarioDTO {
  private Long id;
  private String nome;
  private String email;
  private String matricula;
  private String foto;
  private Boolean ativo;
}
