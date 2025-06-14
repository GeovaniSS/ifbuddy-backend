package br.com.ifbuddy.services;

import java.util.List;

import br.com.ifbuddy.models.CursoSuperior;
import br.com.ifbuddy.repository.CursoRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class CursoService {
  @Inject
  CursoRepository cursoRepository;

  public List<CursoSuperior> listarCursos() {
    return cursoRepository.listAll(Sort.by("nome"));
  }
}
