package br.com.ifbuddy.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "TEMA")
@Entity
public class Tema {
  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TEMA_ID")
  private Long temaId;

  @Column(name = "NOME_TEMA", nullable = false, length = 100, columnDefinition = "VARCHAR(100)")
  private String nomeTema;
}
