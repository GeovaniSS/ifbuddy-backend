package br.com.ifbuddy.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "CURSO_SUPERIOR")
@Entity
public class CursoSuperior {
  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CURSO_ID")
  private Long cursoId;

  @Column(name = "NOME", nullable = false, length = 100, columnDefinition = "VARCHAR(100)")
  private String nome;

  @Column(name = "CAMPUS", nullable = false, length = 100, columnDefinition = "VARCHAR(100)")
  private String campus;

  @Column(name = "DURACAO_SEMESTRES", nullable = false, columnDefinition = "INTEGER")
  private Integer duracaoSemestres;

  // @OneToMany(mappedBy = "curso")
  // private List<Estudante> estudantes;
}
