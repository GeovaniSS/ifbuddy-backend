package br.com.ifbuddy.models;

import java.time.LocalDateTime;

import br.com.ifbuddy.enums.StatusConexao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "CONEXAO")
public class Conexao {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CONEXAO_ID")
  private Long conexaoId;

  @Column(name = "SOLICITANTE_ID", nullable = false, columnDefinition = "INTEGER")
  private Long solicitanteId;

  @Column(name = "SOLICITADO_ID", nullable = false, columnDefinition = "INTEGER")
  private Long solicitadoId;

  @Column(name = "STATUS", nullable = false, length = 1, columnDefinition = "CHAR(1)")
  private StatusConexao status;

  @Column(name = "DATA_SOLICITACAO", nullable = false, columnDefinition = "TIMESTAMP")
  private LocalDateTime dataSolicitacao;

  @Column(name = "DATA_ACEITE", columnDefinition = "TIMESTAMP")
  private LocalDateTime dataAceite;

  @Column(name = "DATA_ENCERRAMENTO", columnDefinition = "TIMESTAMP")
  private LocalDateTime dataEncerramento;

  @Column(name = "ATIVO", nullable = false, columnDefinition = "BOOLEAN")
  private Boolean ativo;
}