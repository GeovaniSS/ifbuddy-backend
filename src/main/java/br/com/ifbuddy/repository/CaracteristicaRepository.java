package br.com.ifbuddy.repository;

import br.com.ifbuddy.models.CaracteristicaPessoal;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CaracteristicaRepository implements PanacheRepository<CaracteristicaPessoal> {
}
