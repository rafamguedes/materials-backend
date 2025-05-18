package com.materials.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_address")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String logradouro;
  private String numero;
  private String bairro;
  private String localidade;
  private String uf;
  private String estado;
  private String cep;
  private String complemento;
  private String regiao;
  private String ibge;
  private String gia;
  private String ddd;
  private String siafi;
}
