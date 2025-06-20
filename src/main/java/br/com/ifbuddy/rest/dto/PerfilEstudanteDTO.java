package br.com.ifbuddy.rest.dto;

import java.time.LocalDate;
import java.util.List;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class PerfilEstudanteDTO {
  private Long estudanteId;
  private String telefone;
  private String foto;
  private String genero;
  private LocalDate dataNascimento;
  private String uf;
  private String cidade;
  private String bairro;
  private Boolean trabalha;
  private String ocupacao;
  private Long cursoId;
  private String turno;
  private Integer semestreAtual;
  private String tipoTCC;
  private String objetivoTCC;
  private String descricao;
  private List<Long> temasIds;
  private List<Long> pontosFortesIds;
  private List<Long> pontosFracosIds;
  private List<CriarDisponibilidadeDTO> disponibilidades;
}
