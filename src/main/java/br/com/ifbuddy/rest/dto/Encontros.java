package br.com.ifbuddy.rest.dto;

import lombok.Data;

@Data
public class Encontros {
  private Boolean presencial = false;
  private Boolean online = false;
}
