package br.com.ifbuddy.rest.dto;

import lombok.Data;

@Data
public class LoginDTO {
  private String email;
  private String senha;
}
