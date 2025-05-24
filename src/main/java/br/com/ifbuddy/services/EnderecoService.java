package br.com.ifbuddy.services;

import java.util.List;

import br.com.ifbuddy.models.Endereco;
import br.com.ifbuddy.repository.EnderecoRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@RequestScoped
public class EnderecoService {
  @Inject
  EnderecoRepository enderecoRepository;

  public List<Endereco> listarEnderecos() {
    return enderecoRepository.listAll();
  }

  @Transactional
  public Endereco criarEndereco(String uf, String cidade, String bairro) {
    Endereco endereco = new Endereco();
    endereco.setUf(uf);
    endereco.setCidade(cidade);
    endereco.setBairro(bairro);
    enderecoRepository.persist(endereco);
    return endereco;
  }
}
