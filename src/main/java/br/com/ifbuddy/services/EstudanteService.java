package br.com.ifbuddy.services;

import java.util.List;
import java.util.stream.Collectors;

import br.com.ifbuddy.enums.Horario;
import br.com.ifbuddy.enums.TipoCaracteristica;
import br.com.ifbuddy.enums.TipoEncontro;
import br.com.ifbuddy.exception.BusinessException;
import br.com.ifbuddy.models.Estudante;
import br.com.ifbuddy.models.Tema;
import br.com.ifbuddy.repository.CaracteristicaRepository;
import br.com.ifbuddy.repository.CursoRepository;
import br.com.ifbuddy.repository.EstudanteRepository;
import br.com.ifbuddy.repository.TemaRepository;
import br.com.ifbuddy.rest.dto.DisponibilidadeDTO;
import br.com.ifbuddy.rest.dto.EstudanteDTO;
import br.com.ifbuddy.rest.dto.EstudanteListaDTO;
import br.com.ifbuddy.rest.dto.FiltrosDTO;
import br.com.ifbuddy.utils.BlobUtils;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@RequestScoped
public class EstudanteService {
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

  public List<EstudanteListaDTO> listarEstudantes(FiltrosDTO filtrosDTO) {
    boolean ignorarFiltroTemas = filtrosDTO.getTemasIds().isEmpty();
    boolean ignorarFiltroCaracteristicas = filtrosDTO.getPontosFortesIds().isEmpty();

    TypedQuery<Estudante> query = entityManager.createNamedQuery("Estudante.buscarPorFiltros",
        Estudante.class);

    query.setParameter("estudanteId", filtrosDTO.getEstudanteId());
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

  public EstudanteDTO consultarEstudante(Long id) {
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
          disponibilidadeDTO.setEncontros(getEncontrosTexto(d.getEncontros()));
          disponibilidadeDTO.setHorarios(getHorariosTexto(d.getHorarios()));
          return disponibilidadeDTO;
        }).collect(Collectors.toList());

    estudanteDTO.setEstudanteId(estudante.getEstudanteId());
    estudanteDTO.setNome(estudante.getNome());
    estudanteDTO.setFoto(BlobUtils.blobParaString(estudante.getFoto()));
    estudanteDTO.setNomeCurso(estudante.getCurso() != null ? estudante.getCurso().getNome() : null);
    estudanteDTO.setTurno(estudante.getTurno() != null ? estudante.getTurno().getDescricao() : null);
    estudanteDTO.setSemestreAtual(estudante.getSemestreAtual());
    estudanteDTO.setDescricao(estudante.getDescricao());
    estudanteDTO.setUf(estudante.getUf());
    estudanteDTO.setCidade(estudante.getCidade());
    estudanteDTO.setBairro(estudante.getBairro());
    estudanteDTO.setTrabalha(estudante.getTrabalha());
    estudanteDTO.setOcupacao(estudante.getOcupacao() == null ? "" : estudante.getOcupacao());
    estudanteDTO.setTipoTCC(estudante.getTipoTCC());
    estudanteDTO.setObjetivoTCC(estudante.getObjetivoTCC());
    estudanteDTO.setTemas(temas);
    estudanteDTO.setPontosFortes(pontosFortes);
    estudanteDTO.setPontosFracos(pontosFracos);
    estudanteDTO.setDisponibilidades(disponibilidades);

    return estudanteDTO;
  }

  private String getHorariosTexto(String horarios) {
    if (horarios == null || horarios.isBlank())
      return "";

    return horarios.chars()
        .mapToObj(t -> Horario.fromKey(String.valueOf((char) t)).getDescricao())
        .collect(Collectors.joining(", "));
  }

  private String getEncontrosTexto(String encontros) {
    if (encontros == null || encontros.isBlank())
      return "";

    return encontros.chars()
        .mapToObj(e -> TipoEncontro.fromKey(String.valueOf((char) e)).getDescricao())
        .collect(Collectors.joining(", "));
  }
}
