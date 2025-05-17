package br.com.ifbuddy.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "ESTUDANTE")
@Entity
public class Estudante {
  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ESTUDANTE_ID")
  private Long estudanteId;

  @Column(name = "MATRICULA", nullable = false, unique = true, length = 20)
  private String matricula;

  @Column(name = "NOME", nullable = false, length = 255)
  private String nome;

  @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
  private String email;

  @Column(name = "SENHA_HASH", nullable = false, length = 255)
  private String senhaHash;
}
