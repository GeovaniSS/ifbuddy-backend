package br.com.ifbuddy.rest.dto;

import java.util.List;

import br.com.ifbuddy.enums.TipoTCC;
import lombok.Data;

@Data
public class EstudanteDTO {
  private Long estudanteId;
  private String nome;
  private String foto;
  private String nomeCurso;
  private String turno;
  private Integer semestreAtual;
  private String descricao;
  private String uf;
  private String cidade;
  private String bairro;
  private String ocupacao;
  private Boolean trabalha;
  private TipoTCC tipoTCC;
  private String objetivoTCC;
  private List<String> temas;
  private List<String> pontosFortes;
  private List<String> pontosFracos;
  private List<DisponibilidadeDTO> disponibilidades;
}
