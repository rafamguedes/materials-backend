package com.materials.api.controller;

import com.materials.api.controller.dto.UserFilterDTO;
import com.materials.api.controller.dto.UserRequestDTO;
import com.materials.api.pagination.PaginationDTO;
import com.materials.api.service.UserService;
import com.materials.api.service.dto.UserDTO;
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
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserDTO> create(@Valid @RequestBody UserRequestDTO requestDTO) {
    var response = userService.create(requestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  public ResponseEntity<PaginationDTO<UserDTO>> findByFilter(@Valid UserFilterDTO filter) {
    var response = userService.findByFilter(filter);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
    var response = userService.getById(id);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDTO> update(
      @PathVariable Long id, @Valid @RequestBody UserRequestDTO requestDTO) {
    var response = userService.update(id, requestDTO);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> softDelete(@PathVariable Long id) {
    userService.softDelete(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> hardDelete(@PathVariable Long id) {
    userService.hardDelete(id);
    return ResponseEntity.noContent().build();
  }
}
