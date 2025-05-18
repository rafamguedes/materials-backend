package com.materials.api.controller.dto;

import com.materials.api.enums.ItemTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemRequestDTO {

  @NotEmpty
  @Size(max = 100, message = "O nome deve ter menos de 100 caracteres")
  private String name;

  @NotEmpty
  @Size(max = 255, message = "A descrição deve ter menos de 255 caracteres")
  private String description;

  @NotNull(message = "O tipo do item não pode ser nulo")
  private ItemTypeEnum type;
}
