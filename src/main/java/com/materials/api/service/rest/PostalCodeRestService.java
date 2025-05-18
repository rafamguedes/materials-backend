package com.materials.api.service.rest;

import com.materials.api.entity.Address;
import com.materials.api.service.exceptions.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PostalCodeRestService {
  private static final String PATH_URL = "https://viacep.com.br/ws/{cep}/json/";

  @Autowired
  private final RestTemplate restTemplate;

  public Address getAddressByPostalCode(String postalCode) {
    try {
      return restTemplate.getForObject(PATH_URL, Address.class, postalCode);
    } catch (GeneralException e) {
      throw new GeneralException(e.getMessage());
    }
  }
}
