package br.com.ifbuddy.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.ifbuddy.enums.Genero;
import br.com.ifbuddy.enums.TipoCaracteristica;
import br.com.ifbuddy.enums.TipoTCC;
import br.com.ifbuddy.enums.Turno;
import br.com.ifbuddy.enums.TurnoDia;
import br.com.ifbuddy.exception.BusinessException;
import br.com.ifbuddy.models.CaracteristicaPessoal;
import br.com.ifbuddy.models.CursoSuperior;
import br.com.ifbuddy.models.Disponibilidade;
import br.com.ifbuddy.models.Endereco;
import br.com.ifbuddy.models.Estudante;
import br.com.ifbuddy.models.EstudanteCaracteristica;
import br.com.ifbuddy.models.EstudanteCaracteristicaId;
import br.com.ifbuddy.models.Tema;
import br.com.ifbuddy.repository.CaracteristicaRepository;
import br.com.ifbuddy.repository.CursoRepository;
import br.com.ifbuddy.repository.EstudanteRepository;
import br.com.ifbuddy.repository.TemaRepository;
import br.com.ifbuddy.rest.dto.DisponibilidadeDTO;
import br.com.ifbuddy.rest.dto.EstudanteDTO;
import br.com.ifbuddy.rest.dto.EstudanteListaDTO;
import br.com.ifbuddy.rest.dto.FiltrosDTO;
import br.com.ifbuddy.rest.dto.PerfilEstudanteDTO;
import br.com.ifbuddy.utils.BlobUtils;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@RequestScoped
public class EstudanteService {
  @Inject
  EstudanteRepository estudanteRepository;

  @Inject
  TemaRepository temaRepository;

  @Inject
  CursoRepository cursoRepository;

  @Inject
  EnderecoService enderecoService;

  @Inject
  CaracteristicaRepository caracteristicaRepository;

  @Inject
  DisponibilidadeService disponibilidadeService;

  @Inject
  EntityManager entityManager;

  public List<EstudanteListaDTO> listarEstudantes(FiltrosDTO filtrosDTO) {
    boolean ignorarFiltroTemas = filtrosDTO.getTemasIds().isEmpty();
    boolean ignorarFiltroCaracteristicas = filtrosDTO.getPontosFortesIds().isEmpty();

    TypedQuery<Estudante> query = entityManager.createNamedQuery("Estudante.buscarPorFiltros",
        Estudante.class);

    query.setParameter("cursoId", filtrosDTO.getCursoId() == 0 ? null : filtrosDTO.getCursoId());
    query.setParameter("turno", filtrosDTO.getTurno() == "" ? null : filtrosDTO.getTurno());
    query.setParameter("semestre", filtrosDTO.getSemestre() == 0 ? null : filtrosDTO.getSemestre());
    query.setParameter("tipoTcc", filtrosDTO.getTipoTCC() == "" ? null : filtrosDTO.getTipoTCC());
    query.setParameter("temasIds", ignorarFiltroTemas ? List.of(-1L) : filtrosDTO.getTemasIds());
    query.setParameter("ignorarFiltroTemas", ignorarFiltroTemas);
    query.setParameter("pontosFortesIds",
        ignorarFiltroCaracteristicas ? List.of(-1L) : filtrosDTO.getPontosFortesIds());
    query.setParameter("ignorarFiltroCaracteristicas", ignorarFiltroCaracteristicas);
    query.setParameter("tipoCaracteristica", TipoCaracteristica.FORTE.getKey());
    query.setParameter("ignorarTipoCaracteristica", ignorarFiltroCaracteristicas);

    List<Estudante> estudantes = query.getResultList();

    List<EstudanteListaDTO> estudantesDTO = estudantes.stream()
        .map(estudante -> {
          EstudanteListaDTO dto = new EstudanteListaDTO();
          dto.setEstudanteId(estudante.getEstudanteId());
          dto.setNome(estudante.getNome());
          dto.setFoto(BlobUtils.blobParaString(estudante.getFoto()));
          dto.setNomeCurso(estudante.getCurso().getNome());
          dto.setTurno(estudante.getTurno().getDescricao());
          dto.setSemestreAtual(estudante.getSemestreAtual());
          dto.setTemas(estudante.getTemas().stream()
              .map(t -> t.getNomeTema())
              .collect(Collectors.toList()));
          return dto;
        })
        .collect(Collectors.toList());

    return estudantesDTO;
  }

  public EstudanteDTO buscarEstudantePorId(Long id) {
    Estudante estudante = estudanteRepository.findByIdOptional(id)
        .orElseThrow(() -> new BusinessException("Estudante não encontrado para o ID: " + id));

    EstudanteDTO estudanteDTO = new EstudanteDTO();
    List<String> temas = estudante.getTemas().stream()
        .map(Tema::getNomeTema)
        .collect(Collectors.toList());
    List<String> pontosFortes = estudante.getCaracteristicas().stream()
        .filter(ec -> ec.getTipoCaracteristica() == TipoCaracteristica.FORTE)
        .map(ec -> ec.getCaracteristicaPessoal().getDescricao())
        .collect(Collectors.toList());
    List<String> pontosFracos = estudante.getCaracteristicas().stream()
        .filter(ec -> ec.getTipoCaracteristica() == TipoCaracteristica.FRACA)
        .map(ec -> ec.getCaracteristicaPessoal().getDescricao())
        .collect(Collectors.toList());
    List<DisponibilidadeDTO> disponibilidades = estudante.getDisponibilidades().stream()
        .map(d -> {
          DisponibilidadeDTO disponibilidadeDTO = new DisponibilidadeDTO();
          disponibilidadeDTO.setDiaSemana(d.getDiaSemana().getKey());
          disponibilidadeDTO.setTextoDiaSemana(d.getDiaSemana().getDescricao());
          disponibilidadeDTO.setTipoEncontro(d.getTipoEncontro().getDescricao());
          disponibilidadeDTO.setTurnos(getTurnosTexto(d.getTurnos()));
          return disponibilidadeDTO;
        }).collect(Collectors.toList());

    estudanteDTO.setEstudanteId(estudante.getEstudanteId());
    estudanteDTO.setNome(estudante.getNome());
    estudanteDTO.setFoto(BlobUtils.blobParaString(estudante.getFoto()));
    estudanteDTO.setNomeCurso(estudante.getCurso().getNome());
    estudanteDTO.setTurno(estudante.getTurno().getDescricao());
    estudanteDTO.setSemestreAtual(estudante.getSemestreAtual());
    estudanteDTO.setDescricao(estudante.getDescricao());
    estudanteDTO.setEndereco(estudante.getEndereco());
    estudanteDTO.setTrabalha(estudante.getTrabalha());
    estudanteDTO.setOcupacao(estudante.getOcupacao());
    estudanteDTO.setTipoTCC(estudante.getTipoTCC());
    estudanteDTO.setObjetivoTCC(estudante.getObjetivoTCC());
    estudanteDTO.setTemas(temas);
    estudanteDTO.setPontosFortes(pontosFortes);
    estudanteDTO.setPontosFracos(pontosFracos);
    estudanteDTO.setDisponibilidades(disponibilidades);

    return estudanteDTO;
  }

  @Transactional
  public Estudante atualizarPerfilEstudante(PerfilEstudanteDTO dto) {
    Long estudanteId = dto.getEstudanteId();
    Estudante estudante = estudanteRepository.buscarEstudantePorId(estudanteId)
        .orElseThrow(() -> new BusinessException("Estudante não encontrado para o ID: " + estudanteId));

    atualizarDadosPessoais(estudante, dto);
    atualizarEndereco(estudante, dto);
    atualizarCurso(estudante, dto);
    atualizarTemas(estudante, dto);
    atualizarCaracteristicas(estudante, dto);
    atualizarDisponibilidade(estudante, dto);

    return estudante;
  }

  private String getTurnosTexto(String turnos) {
    if (turnos == null || turnos.isBlank())
      return "";

    return turnos.chars()
        .mapToObj(t -> TurnoDia.fromKey(String.valueOf((char) t)).getDescricao())
        .collect(Collectors.joining(", "));
  }

  private void atualizarDadosPessoais(Estudante estudante, PerfilEstudanteDTO dto) {
    if (dto.getFoto() != null && !dto.getFoto().isBlank()) {
      estudante.setFoto(BlobUtils.stringParaBlob(dto.getFoto()));
    }
    estudante.setTelefone(dto.getTelefone());
    estudante.setGenero(Genero.fromKey(dto.getGenero()));
    estudante.setDataNascimento(dto.getDataNascimento());
    estudante.setTrabalha(dto.getTrabalha());
    estudante.setOcupacao(dto.getOcupacao());
    estudante.setSemestreAtual(dto.getSemestreAtual());
    estudante.setTipoTCC(TipoTCC.fromKey(dto.getTipoTCC()));
    estudante.setTurno(Turno.fromKey(dto.getTurno()));
    estudante.setObjetivoTCC(dto.getObjetivoTCC());
    estudante.setDescricao(dto.getDescricao());
  }

  private void atualizarEndereco(Estudante estudante, PerfilEstudanteDTO dto) {
    Endereco endereco = enderecoService.criarEndereco(dto.getUf(), dto.getCidade(), dto.getBairro());
    estudante.setEndereco(endereco);
  }

  private void atualizarCurso(Estudante estudante, PerfilEstudanteDTO dto) {
    CursoSuperior curso = cursoRepository.findById(dto.getCursoId());
    if (curso == null) {
      throw new IllegalArgumentException("Curso não encontrado com ID: " + dto.getCursoId());
    }
    estudante.setCurso(curso);
  }

  private void atualizarTemas(Estudante estudante, PerfilEstudanteDTO dto) {
    if (dto.getTemasIds() != null && !dto.getTemasIds().isEmpty()) {
      Set<Tema> temas = temaRepository.buscarTemasPorIds(dto.getTemasIds());
      estudante.setTemas(temas);
    } else {
      estudante.setTemas(Set.of());
    }
  }

  private void atualizarCaracteristicas(Estudante estudante, PerfilEstudanteDTO dto) {
    Set<EstudanteCaracteristica> caracteristicas = new HashSet<>();

    caracteristicas.addAll(criarCaracteristicas(estudante, dto.getPontosFortesIds(), TipoCaracteristica.FORTE));
    caracteristicas.addAll(criarCaracteristicas(estudante, dto.getPontosFracosIds(), TipoCaracteristica.FRACA));

    estudante.getCaracteristicas().clear();

    for (EstudanteCaracteristica caracteristica : caracteristicas) {
      EstudanteCaracteristica managed = entityManager.merge(caracteristica);
      estudante.getCaracteristicas().add(managed);
    }
  }

  private Set<EstudanteCaracteristica> criarCaracteristicas(
      Estudante estudante, List<Long> ids, TipoCaracteristica tipo) {

    if (ids == null || ids.isEmpty())
      return Set.of();

    return ids.stream().map(caracteristicaId -> {
      CaracteristicaPessoal caracteristica = caracteristicaRepository.findById(caracteristicaId);
      if (caracteristica == null) {
        throw new IllegalArgumentException("Característica não encontrada: ID " + caracteristicaId);
      }

      EstudanteCaracteristicaId id = new EstudanteCaracteristicaId();
      id.setEstudanteId(estudante.getEstudanteId());
      id.setCaracteristicaId(caracteristicaId);

      EstudanteCaracteristica ec = new EstudanteCaracteristica();
      ec.setId(id);
      ec.setEstudante(estudante);
      ec.setCaracteristicaPessoal(caracteristica);
      ec.setTipoCaracteristica(tipo);

      return ec;
    }).collect(Collectors.toSet());
  }

  private void atualizarDisponibilidade(Estudante estudante, PerfilEstudanteDTO dto) {
    estudante.getDisponibilidades().clear();
    if (dto.getDisponibilidades() != null && !dto.getDisponibilidades().isEmpty()) {
      Set<Disponibilidade> disponibilidades = disponibilidadeService.criarDisponibilidades(dto.getDisponibilidades());
      estudante.setDisponibilidades(disponibilidades);
    } else {
      estudante.setDisponibilidades(Set.of());
    }
  }
}
