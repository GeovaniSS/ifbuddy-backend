package br.com.ifbuddy.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "CARACTERISTICA_PESSOAL")
@Entity
public class CaracteristicaPessoal {
  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CARACTERISTICA_ID")
  private Long caracteristicaId;

  @Column(name = "DESCRICAO", nullable = false, length = 100, columnDefinition = "VARCHAR(100)")
  private String descricao;
}
