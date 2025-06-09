package br.com.ifbuddy.rest.dto;

import java.util.List;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class EstudanteListaDTO {
  private Long estudanteId;
  private String nome;
  private String foto;
  private String nomeCurso;
  private String turno;
  private Integer semestreAtual;
  private List<String> temas;
}
