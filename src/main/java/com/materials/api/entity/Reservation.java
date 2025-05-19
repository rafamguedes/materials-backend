package com.materials.api.entity;

import com.materials.api.enums.ReservationStatusEnum;
import com.materials.api.service.dto.ReservationDTO;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@SqlResultSetMappings({
  @SqlResultSetMapping(
      name = Reservation.RESERVATION_DTO_MAPPING,
      classes = {
        @ConstructorResult(
            targetClass = ReservationDTO.class,
            columns = {
              @ColumnResult(name = "id", type = Long.class),
              @ColumnResult(name = "dateTime", type = LocalDateTime.class),
              @ColumnResult(name = "code", type = String.class),
              @ColumnResult(name = "status", type = String.class),
              @ColumnResult(name = "userId", type = Long.class),
              @ColumnResult(name = "itemId", type = Long.class)
            })
      })
})
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_reservation")
public class Reservation implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  public static final String RESERVATION_DTO_MAPPING = "ReservationDTO";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "date_time")
  private LocalDateTime dateTime;

  @Column(name = "code")
  private String code;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private ReservationStatusEnum status;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item item;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "deleted")
  private Boolean deleted;
}
