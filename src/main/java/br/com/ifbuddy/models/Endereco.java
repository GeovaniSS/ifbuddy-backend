package br.com.ifbuddy.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "ENDERECO")
@Entity
public class Endereco implements Serializable {
  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ENDERECO_ID")
  private Long enderecoId;

  @Column(name = "UF", nullable = false, length = 2, columnDefinition = "CHAR(2)")
  private String uf;

  @Column(name = "CIDADE", nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
  private String cidade;

  @Column(name = "BAIRRO", nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
  private String bairro;
}
