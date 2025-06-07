package br.com.ifbuddy.repository;

import br.com.ifbuddy.models.CursoSuperior;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CursoRepository implements PanacheRepository<CursoSuperior> {

}
