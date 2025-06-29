package com.materials.api.controller;

import com.materials.api.controller.dto.UserFilterDTO;
import com.materials.api.controller.dto.UserRequestDTO;
import com.materials.api.controller.dto.UserUpdateRequestDTO;
import com.materials.api.pagination.PaginationDTO;
import com.materials.api.service.UserService;
import com.materials.api.service.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Users", description = "Endpoints for managing users in the application")
public class UserController {
  private final UserService userService;

  @PostMapping
  @Operation(description = "Creates a new user in the application.")
  public ResponseEntity<UserDTO> create(@Valid @RequestBody UserRequestDTO requestDTO) {
    var response = userService.create(requestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  @Operation(description = "Retrieves a paginated list of users based on the provided filter.")
  public ResponseEntity<PaginationDTO<UserDTO>> findByFilter(@Valid UserFilterDTO filter) {
    var response = userService.findByFilter(filter);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  @Operation(description = "Retrieves a user by their ID.")
  public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
    var response = userService.getById(id);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  @Operation(description = "Updates an existing user by their ID.")
  public ResponseEntity<UserDTO> update(
      @PathVariable Long id, @Valid @RequestBody UserUpdateRequestDTO requestDTO) {
    var response = userService.update(id, requestDTO);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  @Operation(description = "Soft deletes a user by their ID. The user is marked as deleted but not removed from the database.")
  public ResponseEntity<Void> softDelete(@PathVariable Long id) {
    userService.softDelete(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/delete/{id}")
  @Operation(description = "Hard deletes a user by their ID. The user is permanently removed from the database.")
  public ResponseEntity<Void> hardDelete(@PathVariable Long id) {
    userService.hardDelete(id);
    return ResponseEntity.noContent().build();
  }
}
