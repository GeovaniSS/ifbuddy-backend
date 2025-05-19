package br.com.ifbuddy.services;

import java.util.List;

import br.com.ifbuddy.models.Endereco;
import br.com.ifbuddy.repository.EnderecoRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class EnderecoService {
  @Inject
  EnderecoRepository enderecoRepository;

  public List<Endereco> listarEnderecos() {
    return enderecoRepository.listAll();
  }
}
