package br.com.ifbuddy.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.ifbuddy.enums.DiaSemana;
import br.com.ifbuddy.models.Disponibilidade;
import br.com.ifbuddy.repository.DisponibilidadeRepository;
import br.com.ifbuddy.rest.dto.CriarDisponibilidadeDTO;
import br.com.ifbuddy.rest.dto.Encontros;
import br.com.ifbuddy.rest.dto.Horarios;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class DisponibilidadeService {
  @Inject
  DisponibilidadeRepository disponibilidadeRepository;

  public List<CriarDisponibilidadeDTO> montarDisponibilidadesDoEstudante(
      Set<Disponibilidade> disponibilidadesEstudante) {
    List<CriarDisponibilidadeDTO> disponibilidades = new ArrayList<>();

    for (DiaSemana dia : DiaSemana.values()) {
      CriarDisponibilidadeDTO dto = new CriarDisponibilidadeDTO();
      dto.setDiaSemana(dia.getKey());
      dto.setTextoDiaSemana(dia.getDescricao());

      Optional<Disponibilidade> disponibilidadeEstudanteOpt = disponibilidadesEstudante.stream()
          .filter(d -> d.getDiaSemana().getKey() == dia.getKey())
          .findFirst();

      if (disponibilidadeEstudanteOpt.isPresent()) {
        Disponibilidade disponibilidade = disponibilidadeEstudanteOpt.get();
        dto.setAtivo(true);
        dto.setHorarios(criarHorariosDTO(disponibilidade.getHorarios()));
        dto.setEncontros(criarEncontrosDTO(disponibilidade.getEncontros()));
      } else {
        dto.setAtivo(false);
        dto.setHorarios(new Horarios());
        dto.setEncontros(new Encontros());
      }

      disponibilidades.add(dto);
    }

    return disponibilidades;
  }

  public Set<Disponibilidade> criarDisponibilidades(List<CriarDisponibilidadeDTO> disponibilidadesDTO) {
    Set<Disponibilidade> disponibilidades = disponibilidadesDTO.stream()
        .filter(disponibilidade -> disponibilidade.getAtivo() == true)
        .map(this::criarDisponibilidade)
        .collect(Collectors.toSet());
    disponibilidadeRepository.persist(disponibilidades);
    return disponibilidades;
  }

  private Disponibilidade criarDisponibilidade(CriarDisponibilidadeDTO disponibilidadeDTO) {
    Disponibilidade disponibilidade = new Disponibilidade();
    disponibilidade.setDiaSemana(DiaSemana.fromKey(disponibilidadeDTO.getDiaSemana()));
    disponibilidade.setEncontros(formatarEncontros(disponibilidadeDTO.getEncontros()));
    disponibilidade.setHorarios(formatarHorarios(disponibilidadeDTO.getHorarios()));
    return disponibilidade;
  }

  private Horarios criarHorariosDTO(String horarios) {
    Horarios horariosDTO = new Horarios();

    if (horarios.contains("M")) {
      horariosDTO.setManha(true);
    }

    if (horarios.contains("T")) {
      horariosDTO.setTarde(true);
    }

    if (horarios.contains("N")) {
      horariosDTO.setNoite(true);
    }

    return horariosDTO;
  }

  private Encontros criarEncontrosDTO(String encontros) {
    Encontros encontrosDTO = new Encontros();

    if (encontros.contains("O")) {
      encontrosDTO.setOnline(true);
    }

    if (encontros.contains("P")) {
      encontrosDTO.setPresencial(true);
    }

    return encontrosDTO;
  }

  private String formatarEncontros(Encontros encontros) {
    StringBuilder resultado = new StringBuilder();

    if (encontros.getOnline()) {
      resultado.append("O");
    }

    if (encontros.getPresencial()) {
      resultado.append("P");
    }

    return resultado.toString();
  }

  private String formatarHorarios(Horarios horarios) {
    StringBuilder resultado = new StringBuilder();

    if (horarios.getManha()) {
      resultado.append("M");
    }

    if (horarios.getTarde()) {
      resultado.append("T");
    }

    if (horarios.getNoite()) {
      resultado.append("N");
    }

    return resultado.toString();
  }
}
