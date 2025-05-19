package br.com.ifbuddy.repository;

import br.com.ifbuddy.models.Tema;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TemaRepository implements PanacheRepository<Tema> {
}
