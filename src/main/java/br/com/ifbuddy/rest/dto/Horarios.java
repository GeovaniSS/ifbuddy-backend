package br.com.ifbuddy.rest.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class Horarios {
  private Boolean manha = false;
  private Boolean tarde = false;
  private Boolean noite = false;
}
