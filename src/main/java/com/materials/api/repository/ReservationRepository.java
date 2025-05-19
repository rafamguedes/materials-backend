package com.materials.api.repository;

import com.materials.api.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository
    extends JpaRepository<Reservation, Long>, ReservationCustomRepository {
  Optional<Reservation> findByCode(String code);
}
