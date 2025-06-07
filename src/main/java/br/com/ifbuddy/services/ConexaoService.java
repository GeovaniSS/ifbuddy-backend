package br.com.ifbuddy.services;

import java.time.LocalDateTime;
import java.util.List;

import br.com.ifbuddy.enums.StatusConexao;
import br.com.ifbuddy.models.Conexao;
import br.com.ifbuddy.models.Estudante;
import br.com.ifbuddy.repository.ConexaoRepository;
import br.com.ifbuddy.repository.EstudanteRepository;
import br.com.ifbuddy.rest.dto.ConexaoDTO;
import br.com.ifbuddy.rest.dto.ConexaoUsuarioDTO;
import br.com.ifbuddy.rest.dto.GerenciarConexaoDTO;
import br.com.ifbuddy.rest.dto.SolicitarConexaoDTO;
import br.com.ifbuddy.utils.DataUtils;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@RequestScoped
public class ConexaoService {
  @Inject
  ConexaoRepository conexaoRepository;

  @Inject
  EstudanteRepository estudanteRepository;

  @Transactional
  public List<ConexaoDTO> listarSolicitacoesDeConexao(Long estudanteId) {
    List<Conexao> listaSolicitacoesDeConexao = conexaoRepository
        .buscarSolicitacoesDeConexao(estudanteId);

    return listaSolicitacoesDeConexao.stream().map(c -> {
      Estudante solicitante = estudanteRepository.findById(c.getSolicitanteId());

      ConexaoDTO conexaoDTO = new ConexaoDTO();
      ConexaoUsuarioDTO parceiroDTO = new ConexaoUsuarioDTO();

      parceiroDTO.setId(solicitante.getEstudanteId());
      parceiroDTO.setFoto(solicitante.getFoto() != null ? solicitante.getFoto().toString() : null);
      parceiroDTO.setNome(solicitante.getNome());

      conexaoDTO.setConexaoId(c.getConexaoId());
      conexaoDTO.setParceiro(parceiroDTO);
      conexaoDTO.setStatus(c.getStatus());
      conexaoDTO.setDataSolicitacao(DataUtils.formatarDataHora(c.getDataSolicitacao()));
      conexaoDTO.setDataAceite(DataUtils.formatarDataHora(c.getDataAceite()));
      conexaoDTO.setDataEncerramento(DataUtils.formatarDataHora(c.getDataEncerramento()));
      conexaoDTO.setAtivo(c.getAtivo());

      return conexaoDTO;
    }).toList();
  }

  @Transactional
  public List<ConexaoDTO> listarConexoesConfirmadas(Long estudanteId) {
    List<Conexao> listaConexoesConfirmadas = conexaoRepository
        .buscarConexoesPorStatus(estudanteId, StatusConexao.ACEITA);

    return listaConexoesConfirmadas.stream().map(c -> {
      boolean ehSolicitante = estudanteId.equals(c.getSolicitanteId());

      Long parceiroId = ehSolicitante
          ? c.getSolicitadoId()
          : c.getSolicitanteId();

      Estudante parceiro = estudanteRepository.findById(parceiroId);

      ConexaoDTO conexaoDTO = new ConexaoDTO();
      ConexaoUsuarioDTO parceiroDTO = new ConexaoUsuarioDTO();

      parceiroDTO.setId(parceiro.getEstudanteId());
      parceiroDTO.setFoto(parceiro.getFoto() != null ? parceiro.getFoto().toString() : null);
      parceiroDTO.setNome(parceiro.getNome());
      parceiroDTO.setEmail(parceiro.getEmail());
      parceiroDTO.setTelefone(parceiro.getTelefone());

      conexaoDTO.setConexaoId(c.getConexaoId());
      conexaoDTO.setParceiro(parceiroDTO);
      conexaoDTO.setStatus(c.getStatus());
      conexaoDTO.setDataSolicitacao(DataUtils.formatarDataHora(c.getDataSolicitacao()));
      conexaoDTO.setDataAceite(DataUtils.formatarDataHora(c.getDataAceite()));
      conexaoDTO.setDataEncerramento(DataUtils.formatarDataHora(c.getDataEncerramento()));
      conexaoDTO.setAtivo(c.getAtivo());

      return conexaoDTO;
    }).toList();
  }

  @Transactional
  public Conexao solicitarConexao(SolicitarConexaoDTO solicitarConexaoDTO) {
    Long solicitanteId = solicitarConexaoDTO.getSolicitanteId();
    Long solicitadoId = solicitarConexaoDTO.getSolicitadoId();

    if (solicitanteId.equals(solicitadoId)) {
      throw new IllegalArgumentException("Você não pode se conectar consigo mesmo.");
    }

    Conexao conexaoExistente = conexaoRepository.buscarConexaoExistente(solicitanteId, solicitadoId);

    if (conexaoExistente != null && !conexaoExistente.getStatus().equals(StatusConexao.PENDENTE)) {
      throw new IllegalStateException("Já existe uma conexão entre o usuário de ID "
          + solicitanteId + " e o usuário de ID " + solicitadoId);
    }

    if (conexaoExistente != null && conexaoExistente.getSolicitanteId() == solicitanteId) {
      throw new IllegalStateException("Já existe uma solicitação de conexão para o usuário de ID "
          + solicitadoId);
    }

    if (conexaoExistente != null && conexaoExistente.getStatus().equals(StatusConexao.PENDENTE)) {
      GerenciarConexaoDTO gerenciarConexaoDTO = new GerenciarConexaoDTO();
      gerenciarConexaoDTO.setConexaoId(conexaoExistente.getConexaoId());
      gerenciarConexaoDTO.setEstudanteId(solicitanteId);
      return this.aceitarConexao(gerenciarConexaoDTO);
    }

    Conexao novaConexao = new Conexao();

    novaConexao.setSolicitanteId(solicitanteId);
    novaConexao.setSolicitadoId(solicitadoId);
    novaConexao.setDataSolicitacao(LocalDateTime.now());
    novaConexao.setStatus(StatusConexao.PENDENTE);
    novaConexao.setAtivo(true);

    conexaoRepository.persist(novaConexao);

    return novaConexao;
  }

  @Transactional
  public Conexao aceitarConexao(GerenciarConexaoDTO gerenciarConexaoDTO) {
    Long conexaoId = gerenciarConexaoDTO.getConexaoId();
    Long estudanteId = gerenciarConexaoDTO.getEstudanteId();
    Conexao conexao = conexaoRepository.findById(conexaoId);

    if (conexao == null) {
      throw new NotFoundException("Conexão com ID " + conexaoId + " não foi encontrada.");
    }

    if (conexao.getSolicitanteId().equals(estudanteId)) {
      throw new IllegalArgumentException("Você não pode aceitar uma conexão que você mesmo solicitou.");
    }

    Boolean conexaoPendente = conexao.getStatus().equals(StatusConexao.PENDENTE);

    if (!conexaoPendente) {
      throw new IllegalStateException("Conexão não está pendente e não pode ser aceita.");
    }

    conexao.setStatus(StatusConexao.ACEITA);
    conexao.setDataAceite(LocalDateTime.now());

    return conexao;
  }

  @Transactional
  public Conexao recusarConexao(GerenciarConexaoDTO gerenciarConexaoDTO) {
    Long conexaoId = gerenciarConexaoDTO.getConexaoId();
    Long estudanteId = gerenciarConexaoDTO.getEstudanteId();
    Conexao conexao = conexaoRepository.findById(conexaoId);

    if (conexao == null) {
      throw new NotFoundException("Conexão com ID " + conexaoId + " não foi encontrada.");
    }

    if (conexao.getSolicitanteId().equals(estudanteId)) {
      throw new IllegalArgumentException("Você não pode recusar uma conexão que você mesmo solicitou.");
    }

    Boolean conexaoPendente = conexao.getStatus().equals(StatusConexao.PENDENTE);

    if (!conexaoPendente) {
      throw new IllegalStateException("Conexão não está pendente e não pode ser recusada.");
    }

    conexao.setStatus(StatusConexao.RECUSADA);
    conexao.setAtivo(false);

    return conexao;
  }

  @Transactional
  public Conexao desfazerConexao(GerenciarConexaoDTO gerenciarConexaoDTO) {
    Long conexaoId = gerenciarConexaoDTO.getConexaoId();
    Conexao conexao = conexaoRepository.findById(conexaoId);

    if (conexao == null) {
      throw new NotFoundException("Conexão com ID " + conexaoId + " não foi encontrada.");
    }

    Boolean conexaoAtiva = conexao.getStatus().equals(StatusConexao.ACEITA);

    if (!conexaoAtiva) {
      throw new IllegalStateException("Conexão não está ativa e não pode ser desfeita.");
    }

    conexao.setStatus(StatusConexao.CANCELADA);
    conexao.setDataEncerramento(LocalDateTime.now());
    conexao.setAtivo(false);

    return conexao;
  }
}
