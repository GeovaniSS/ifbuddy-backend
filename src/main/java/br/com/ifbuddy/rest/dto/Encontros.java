package br.com.ifbuddy.rest.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class Encontros {
  private Boolean presencial = false;
  private Boolean online = false;
}
