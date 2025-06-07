package br.com.ifbuddy.rest.dto;

import lombok.Data;

@Data
public class ConexaoUsuarioDTO {
  private Long id;
  private String foto;
  private String nome;
  private String email;
  private String telefone;
}
