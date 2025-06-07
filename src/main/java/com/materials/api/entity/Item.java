package com.materials.api.entity;

import com.materials.api.enums.ItemStatusEnum;
import com.materials.api.enums.ItemTypeEnum;
import com.materials.api.service.dto.ItemDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@SqlResultSetMappings({
  @SqlResultSetMapping(
      name = Item.ITEM_DTO_MAPPING,
      classes = {
        @ConstructorResult(
            targetClass = ItemDTO.class,
            columns = {
              @ColumnResult(name = "id", type = Long.class),
              @ColumnResult(name = "name", type = String.class),
              @ColumnResult(name = "description", type = String.class),
              @ColumnResult(name = "itemType", type = String.class),
              @ColumnResult(name = "serialNumber", type = String.class),
              @ColumnResult(name = "status", type = String.class)
            })
      })
})
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_item")
public class Item implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  public static final String ITEM_DTO_MAPPING = "ItemDTOMapping";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "item_type")
  private ItemTypeEnum type;

  @Column(name = "serial_number")
  private String serialNumber;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private ItemStatusEnum status;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
