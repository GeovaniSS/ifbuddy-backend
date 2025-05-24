package br.com.ifbuddy.models;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.ifbuddy.enums.Genero;
import br.com.ifbuddy.enums.TipoTCC;
import br.com.ifbuddy.enums.Turno;
import br.com.ifbuddy.enums.converters.GeneroConverter;
import br.com.ifbuddy.enums.converters.TipoTCCConverter;
import br.com.ifbuddy.enums.converters.TurnoConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "ESTUDANTE")
@Entity
@NamedNativeQuery(name = "Estudante.buscarPorFiltros", query = """
      SELECT DISTINCT e.*
      FROM ESTUDANTE e
      JOIN ESTUDANTE_TEMA et ON e.ESTUDANTE_ID = et.ESTUDANTE_ID
      JOIN ESTUDANTE_CARACTERISTICA ec ON e.ESTUDANTE_ID = ec.ESTUDANTE_ID
      WHERE (:cursoId IS NULL OR e.CURSO_ID = :cursoId)
        AND (:turno IS NULL OR e.TURNO = :turno)
        AND (:semestre IS NULL OR e.SEMESTRE_ATUAL = :semestre)
        AND (:tipoTcc IS NULL OR e.TIPO_TCC = :tipoTcc)
        AND (:ignorarFiltroTemas = TRUE OR et.TEMA_ID IN (:temasIds))
        AND (:ignorarFiltroCaracteristicas = TRUE OR ec.CARACTERISTICA_ID IN (:pontosFortesIds))
        AND (:ignorarTipoCaracteristica = TRUE OR ec.TIPO_CARACTERISTICA = :tipoCaracteristica)
    """, resultClass = Estudante.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estudante {
  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ESTUDANTE_ID")
  private Long estudanteId;

  @Column(name = "MATRICULA", nullable = false, unique = true, length = 20, columnDefinition = "VARCHAR(20)")
  private String matricula;

  @Column(name = "NOME", nullable = false, length = 255, columnDefinition = "VARCHAR(255)")
  private String nome;

  @Column(name = "EMAIL", nullable = false, unique = true, length = 100, columnDefinition = "VARCHAR(100)")
  private String email;

  @Column(name = "SENHA_HASH", nullable = false, length = 255, columnDefinition = "VARCHAR(255)")
  private String senhaHash;

  @Convert(converter = GeneroConverter.class)
  @Column(name = "GENERO", nullable = false, length = 1, columnDefinition = "CHAR(1)")
  private Genero genero;

  @Column(name = "TELEFONE", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
  private String telefone;

  @Column(name = "FOTO", nullable = true, columnDefinition = "BLOB")
  private Blob foto;

  @Column(name = "DATA_NASCIMENTO", nullable = false, columnDefinition = "DATE")
  private LocalDate dataNascimento;

  @Column(name = "SEMESTRE_ATUAL", nullable = true, columnDefinition = "INTEGER")
  private Integer semestreAtual;

  @Convert(converter = TurnoConverter.class)
  @Column(name = "TURNO", nullable = false, length = 1, columnDefinition = "CHAR(1)")
  private Turno turno;

  @Column(name = "TRABALHA", nullable = false, columnDefinition = "BOOLEAN")
  private Boolean trabalha;

  @Column(name = "OCUPACAO", nullable = true, length = 100, columnDefinition = "VARCHAR(100)")
  private String ocupacao;

  @Convert(converter = TipoTCCConverter.class)
  @Column(name = "TIPO_TCC", nullable = false, length = 1, columnDefinition = "CHAR(1)")
  private TipoTCC tipoTCC;

  @Column(name = "OBJETIVO_TCC", nullable = false, length = 100, columnDefinition = "VARCHAR(100)")
  private String objetivoTCC;

  @Column(name = "STATUS_BUSCA", nullable = true, length = 100, columnDefinition = "VARCHAR(100)")
  private String statusBusca;

  @Column(name = "DESCRICAO", nullable = true, length = 500, columnDefinition = "VARCHAR(500)")
  private String descricao;

  @Column(name = "ATIVO", nullable = false, columnDefinition = "BOOLEAN")
  private Boolean ativo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ENDERECO_ID", nullable = false)
  private Endereco endereco;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CURSO_ID", nullable = false)
  private CursoSuperior curso;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "ESTUDANTE_TEMA",
    joinColumns = @JoinColumn(name = "ESTUDANTE_ID"), 
    inverseJoinColumns = @JoinColumn(name = "TEMA_ID")
  )
  private Set<Tema> temas = new HashSet<>();

  @OneToMany(mappedBy = "estudante", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<EstudanteCaracteristica> caracteristicas = new HashSet<>();;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "ESTUDANTE_DISPONIBILIDADE", 
    joinColumns = @JoinColumn(name = "ESTUDANTE_ID"), 
    inverseJoinColumns = @JoinColumn(name = "DISPONIBILIDADE_ID")
  )
  private Set<Disponibilidade> disponibilidades = new HashSet<>();
}
