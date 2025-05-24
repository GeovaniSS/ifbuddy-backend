package br.com.ifbuddy.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.ifbuddy.enums.DiaSemana;
import br.com.ifbuddy.enums.TipoEncontro;
import br.com.ifbuddy.models.Disponibilidade;
import br.com.ifbuddy.repository.DisponibilidadeRepository;
import br.com.ifbuddy.rest.dto.DisponibilidadeDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class DisponibilidadeService {
  @Inject
  DisponibilidadeRepository disponibilidadeRepository;

  public Set<Disponibilidade> criarDisponibilidades(List<DisponibilidadeDTO> disponibilidadesDTO) {
    Set<Disponibilidade> disponibilidades = disponibilidadesDTO.stream()
        .map(this::criarDisponibilidade)
        .collect(Collectors.toSet());
    disponibilidadeRepository.persist(disponibilidades);
    return disponibilidades;
  }

  private Disponibilidade criarDisponibilidade(DisponibilidadeDTO disponibilidadeDTO) {
    Disponibilidade disponibilidade = new Disponibilidade();
    disponibilidade.setDiaSemana(DiaSemana.fromKey(disponibilidadeDTO.getDiaSemana()));
    disponibilidade.setTipoEncontro(TipoEncontro.fromKey(disponibilidadeDTO.getTipoEncontro()));
    disponibilidade.setTurnos(disponibilidadeDTO.getTurnos());
    return disponibilidade;
  }
}
