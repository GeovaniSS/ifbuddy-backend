package br.com.ifbuddy.repository;

import br.com.ifbuddy.models.Disponibilidade;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DisponibilidadeRepository implements PanacheRepository<Disponibilidade> {

}
