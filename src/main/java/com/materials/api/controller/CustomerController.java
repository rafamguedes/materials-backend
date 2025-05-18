package com.materials.api.controller;

import com.materials.api.controller.dto.CustomerRequestDTO;
import com.materials.api.service.CustomerService;
import com.materials.api.service.dto.CustomerDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
  private final CustomerService customerService;

  @PostMapping
  public ResponseEntity<CustomerDTO> create(@Valid @RequestBody CustomerRequestDTO requestDTO) {
    var response = customerService.create(requestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerDTO> getById(@PathVariable Long id) {
    var response = customerService.getById(id);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerDTO> update(
      @PathVariable Long id, @Valid @RequestBody CustomerRequestDTO requestDTO) {
    var response = customerService.update(id, requestDTO);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    customerService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
