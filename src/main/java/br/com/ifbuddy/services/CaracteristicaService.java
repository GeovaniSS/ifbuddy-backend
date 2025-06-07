package br.com.ifbuddy.services;

import java.util.List;

import br.com.ifbuddy.models.CaracteristicaPessoal;
import br.com.ifbuddy.repository.CaracteristicaRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class CaracteristicaService {
  @Inject
  CaracteristicaRepository caracteristicaRepository;

  public List<CaracteristicaPessoal> listarCaracteristicas() {
    return caracteristicaRepository.listAll();
  }
}
