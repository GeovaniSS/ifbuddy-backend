package br.com.ifbuddy.rest.dto;

import lombok.Data;

@Data
public class Horarios {
  private Boolean manha = false;
  private Boolean tarde = false;
  private Boolean noite = false;
}
