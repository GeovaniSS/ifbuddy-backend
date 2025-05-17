package br.com.ifbuddy.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {
  private Long id;
  private String nome;
  private String email;
  private String matricula;
}
