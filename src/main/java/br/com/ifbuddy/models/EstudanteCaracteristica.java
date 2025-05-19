package br.com.ifbuddy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ifbuddy.enums.TipoCaracteristica;
import br.com.ifbuddy.enums.converters.TipoCaracteristicaConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "ESTUDANTE_CARACTERISTICA")
@Entity
public class EstudanteCaracteristica {
  @Id
  @ManyToOne
  @JoinColumn(name = "ESTUDANTE_ID")
  @JsonIgnore
  private Estudante estudante;

  @Id
  @ManyToOne
  @JoinColumn(name = "CARACTERISTICA_ID")
  private CaracteristicaPessoal caracteristicaPessoal;

  @Convert(converter = TipoCaracteristicaConverter.class)
  @Column(name = "TIPO_CARACTERISTICA", nullable = false, length = 1, columnDefinition = "CHAR(1)")
  private TipoCaracteristica tipoCaracteristica;
}
