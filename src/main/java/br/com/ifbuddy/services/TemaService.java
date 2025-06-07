package br.com.ifbuddy.services;

import java.util.List;

import br.com.ifbuddy.models.Tema;
import br.com.ifbuddy.repository.TemaRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped    
public class TemaService {
  @Inject
  TemaRepository temaRepository;

  public List<Tema> listarTemas() {
    return temaRepository.listAll();
  }
}
