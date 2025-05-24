package br.com.ifbuddy.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ifbuddy.enums.DiaSemana;
import br.com.ifbuddy.enums.TipoEncontro;
import br.com.ifbuddy.enums.converters.DiaSemanaConverter;
import br.com.ifbuddy.enums.converters.TipoEncontroConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "DISPONIBILIDADE")
@Entity
public class Disponibilidade {
  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "DISPONIBILIDADE_ID")
  private Long disponibilidadeId;

  @Convert(converter = DiaSemanaConverter.class)
  @Column(name = "DIA_SEMANA", nullable = false, length = 1, columnDefinition = "CHAR(1)")
  private DiaSemana diaSemana;

  @Convert(converter = TipoEncontroConverter.class)
  @Column(name = "TIPO_ENCONTRO", nullable = false, length = 1, columnDefinition = "CHAR(1)")
  private TipoEncontro tipoEncontro;

  @Column(name = "TURNOS", nullable = false, length = 3, columnDefinition = "VARCHAR(3)")
  private String turnos;

  @ManyToMany(mappedBy = "disponibilidades")
  @JsonIgnore
  private Set<Estudante> estudantes = new HashSet<>();
}
