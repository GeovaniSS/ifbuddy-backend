package br.com.ifbuddy.services;

import java.util.List;

import br.com.ifbuddy.enums.TipoCaracteristica;
import br.com.ifbuddy.exception.BusinessException;
import br.com.ifbuddy.models.Estudante;
import br.com.ifbuddy.repository.EstudanteRepository;
import br.com.ifbuddy.repository.TemaRepository;
import br.com.ifbuddy.rest.dto.FiltrosDTO;
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
  EntityManager entityManager;

  public List<Estudante> listarEstudantes(FiltrosDTO filtrosDTO) {
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

    return query.getResultList();
  }

  public Estudante buscarEstudantePorId(Long id) {
    return estudanteRepository.findByIdOptional(id)
      .orElseThrow(() -> new BusinessException("Estudante não encontrado para o ID: " + id));
  }
}
