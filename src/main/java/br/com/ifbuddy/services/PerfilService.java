package br.com.ifbuddy.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.ifbuddy.enums.Genero;
import br.com.ifbuddy.enums.TipoCaracteristica;
import br.com.ifbuddy.enums.TipoTCC;
import br.com.ifbuddy.enums.Turno;
import br.com.ifbuddy.exception.BusinessException;
import br.com.ifbuddy.models.CaracteristicaPessoal;
import br.com.ifbuddy.models.CursoSuperior;
import br.com.ifbuddy.models.Disponibilidade;
import br.com.ifbuddy.models.Estudante;
import br.com.ifbuddy.models.EstudanteCaracteristica;
import br.com.ifbuddy.models.EstudanteCaracteristicaId;
import br.com.ifbuddy.models.Tema;
import br.com.ifbuddy.repository.CaracteristicaRepository;
import br.com.ifbuddy.repository.CursoRepository;
import br.com.ifbuddy.repository.EstudanteRepository;
import br.com.ifbuddy.repository.TemaRepository;
import br.com.ifbuddy.rest.dto.CriarDisponibilidadeDTO;
import br.com.ifbuddy.rest.dto.PerfilEstudanteDTO;
import br.com.ifbuddy.rest.dto.UsuarioDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@RequestScoped
public class PerfilService {
  @Inject
  EstudanteRepository estudanteRepository;

  @Inject
  TemaRepository temaRepository;

  @Inject
  CursoRepository cursoRepository;

  @Inject
  CaracteristicaRepository caracteristicaRepository;

  @Inject
  DisponibilidadeService disponibilidadeService;

  @Inject
  EntityManager entityManager;

  @Transactional
  public PerfilEstudanteDTO consultarPerfilEstudante(Long id) {
    Estudante estudante = estudanteRepository.findByIdOptional(id)
        .orElseThrow(() -> new BusinessException("Estudante não encontrado para o ID: " + id));

    PerfilEstudanteDTO perfilEstudanteDTO = new PerfilEstudanteDTO();

    List<Long> temasIds = estudante.getTemas().stream()
        .map(Tema::getTemaId)
        .collect(Collectors.toList());
    List<Long> pontosFortesIds = estudante.getCaracteristicas().stream()
        .filter(ec -> ec.getTipoCaracteristica() == TipoCaracteristica.FORTE)
        .map(ec -> ec.getCaracteristicaPessoal().getCaracteristicaId())
        .collect(Collectors.toList());
    List<Long> pontosFracosIds = estudante.getCaracteristicas().stream()
        .filter(ec -> ec.getTipoCaracteristica() == TipoCaracteristica.FRACA)
        .map(ec -> ec.getCaracteristicaPessoal().getCaracteristicaId())
        .collect(Collectors.toList());
    List<CriarDisponibilidadeDTO> disponibilidades = disponibilidadeService
        .montarDisponibilidadesDoEstudante(estudante.getDisponibilidades());

    perfilEstudanteDTO.setEstudanteId(estudante.getEstudanteId());
    perfilEstudanteDTO.setTelefone(estudante.getTelefone());
    perfilEstudanteDTO.setFoto(estudante.getFoto());
    perfilEstudanteDTO.setGenero(estudante.getGenero() != null ? estudante.getGenero().getKey() : null);
    perfilEstudanteDTO.setDataNascimento(estudante.getDataNascimento());
    perfilEstudanteDTO.setUf(estudante.getUf());
    perfilEstudanteDTO.setCidade(estudante.getCidade());
    perfilEstudanteDTO.setBairro(estudante.getBairro());
    perfilEstudanteDTO.setTrabalha(estudante.getTrabalha());
    perfilEstudanteDTO.setOcupacao(estudante.getOcupacao());
    perfilEstudanteDTO.setCursoId(estudante.getCurso() != null ? estudante.getCurso().getCursoId() : null);
    perfilEstudanteDTO.setTurno(estudante.getTurno() != null ? estudante.getTurno().getKey() : null);
    perfilEstudanteDTO.setSemestreAtual(estudante.getSemestreAtual());
    perfilEstudanteDTO.setTipoTCC(estudante.getTipoTCC() != null ? estudante.getTipoTCC().getKey() : null);
    perfilEstudanteDTO.setObjetivoTCC(estudante.getObjetivoTCC());
    perfilEstudanteDTO.setDescricao(estudante.getDescricao());
    perfilEstudanteDTO.setTemasIds(temasIds);
    perfilEstudanteDTO.setPontosFortesIds(pontosFortesIds);
    perfilEstudanteDTO.setPontosFracosIds(pontosFracosIds);
    perfilEstudanteDTO.setDisponibilidades(disponibilidades);

    return perfilEstudanteDTO;
  }

  @Transactional
  public UsuarioDTO atualizarPerfilEstudante(PerfilEstudanteDTO dto) {
    Long estudanteId = dto.getEstudanteId();
    Estudante estudante = estudanteRepository.buscarEstudantePorId(estudanteId)
        .orElseThrow(() -> new BusinessException("Estudante não encontrado para o ID: " + estudanteId));

    atualizarDadosPessoais(estudante, dto);
    atualizarCurso(estudante, dto);
    atualizarTemas(estudante, dto);
    atualizarCaracteristicas(estudante, dto);
    atualizarDisponibilidade(estudante, dto);

    estudante.setAtivo(true);

    return new UsuarioDTO(
      estudante.getEstudanteId(),
      estudante.getNome(),
      estudante.getEmail(),
      estudante.getMatricula(),
      estudante.getFoto(),
      estudante.getAtivo()
    );
  }

  private void atualizarDadosPessoais(Estudante estudante, PerfilEstudanteDTO dto) {
    estudante.setFoto(dto.getFoto());
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
    estudante.setUf(dto.getUf());
    estudante.setCidade(dto.getCidade());
    estudante.setBairro(dto.getBairro());
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
