package br.com.ifbuddy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ifbuddy.enums.TipoCaracteristica;
import br.com.ifbuddy.enums.converters.TipoCaracteristicaConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = { "estudante" })
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "ESTUDANTE_CARACTERISTICA")
@Entity
public class EstudanteCaracteristica {
  @EmbeddedId
  @EqualsAndHashCode.Include
  private EstudanteCaracteristicaId id;

  @ManyToOne
  @JoinColumn(name = "ESTUDANTE_ID", insertable = false, updatable = false)
  @JsonIgnore
  private Estudante estudante;

  @ManyToOne
  @JoinColumn(name = "CARACTERISTICA_ID", insertable = false, updatable = false)
  private CaracteristicaPessoal caracteristicaPessoal;

  @Convert(converter = TipoCaracteristicaConverter.class)
  @Column(name = "TIPO_CARACTERISTICA", nullable = false, length = 1, columnDefinition = "CHAR(1)")
  private TipoCaracteristica tipoCaracteristica;
}
