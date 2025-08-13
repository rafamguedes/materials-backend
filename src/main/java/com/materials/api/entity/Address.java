package com.materials.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_address")
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class Address extends GenericEntity {
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
