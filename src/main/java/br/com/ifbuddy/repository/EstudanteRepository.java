package br.com.ifbuddy.repository;

import java.util.Optional;

import br.com.ifbuddy.models.Estudante;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EstudanteRepository implements PanacheRepository<Estudante> {
  public Optional<Estudante> buscarEstudantePorId(Long id) {
    return find("""
          SELECT e FROM Estudante e
          LEFT JOIN FETCH e.curso
          LEFT JOIN FETCH e.endereco
          LEFT JOIN FETCH e.caracteristicas
          LEFT JOIN FETCH e.temas
          LEFT JOIN FETCH e.disponibilidades
          WHERE e.estudanteId = ?1
        """, id).singleResultOptional();
  }

  public Optional<Estudante> findByEmail(String email) {
    return find("email", email).singleResultOptional();
  }

  public Optional<Estudante> findByMatricula(String matricula) {
    return find("matricula", matricula).singleResultOptional();
  }
}
