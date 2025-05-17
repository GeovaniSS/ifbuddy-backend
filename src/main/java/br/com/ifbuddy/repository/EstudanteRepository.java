package br.com.ifbuddy.repository;

import java.util.Optional;

import br.com.ifbuddy.models.Estudante;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EstudanteRepository implements PanacheRepository<Estudante> {
  public Optional<Estudante> findByEmail(String email) {
    return find("email", email).singleResultOptional();
  }

  public Optional<Estudante> findByMatricula(String matricula) {
    return find("matricula", matricula).singleResultOptional();
  }
}
