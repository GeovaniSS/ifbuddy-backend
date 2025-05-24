package br.com.ifbuddy.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class EstudanteCaracteristicaId implements Serializable {
  @Column(name = "ESTUDANTE_ID")
  private Long estudanteId;

  @Column(name = "CARACTERISTICA_ID")
  private Long caracteristicaId;
}