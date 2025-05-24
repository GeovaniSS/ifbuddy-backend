package br.com.ifbuddy.repository;

import java.util.List;

import br.com.ifbuddy.enums.StatusConexao;
import br.com.ifbuddy.models.Conexao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConexaoRepository implements PanacheRepository<Conexao> {
  public Conexao buscarConexaoExistente(Long solicitanteId, Long solicitadoId) {
    return find("(solicitanteId = ?1 and solicitadoId = ?2) or (solicitanteId = ?2 and solicitadoId = ?1)",
        solicitanteId, solicitadoId)
        .firstResult();
  }

  public List<Conexao> buscarConexoesPorStatus(Long estudanteId, StatusConexao statusConexao) {
    return find("(solicitanteId = ?1 or solicitadoId = ?1) and status = ?2", estudanteId, statusConexao)
        .list();
  }

  public List<Conexao> buscarSolicitacoesDeConexao(Long estudanteId) {
    return find("(solicitadoId = ?1) and status = ?2", estudanteId, StatusConexao.PENDENTE)
        .list();
  }
}
