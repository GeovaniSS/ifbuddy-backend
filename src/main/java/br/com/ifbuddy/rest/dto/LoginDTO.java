package br.com.ifbuddy.rest.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class LoginDTO {
  private String email;
  private String senha;
}
