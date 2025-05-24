package br.com.ifbuddy.repository;

import java.util.List;
import java.util.Set;

import br.com.ifbuddy.models.Tema;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TemaRepository implements PanacheRepository<Tema> {
  public Set<Tema> buscarTemasPorIds(List<Long> ids) {
    return find("id in ?1", ids).list()
        .stream()
        .collect(java.util.stream.Collectors.toSet());  
  }
}
