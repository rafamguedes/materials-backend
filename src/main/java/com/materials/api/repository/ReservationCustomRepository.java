package com.materials.api.repository;

import com.materials.api.controller.dto.ReservationFilterDTO;

import java.util.List;

import com.materials.api.controller.dto.ReservationReportFilterDTO;
import com.materials.api.entity.Reservation;
import com.materials.api.service.dto.ReservationDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationCustomRepository {
  List<ReservationDTO> findByFilter(ReservationFilterDTO filter);
  List<Reservation> generateReservationReport(ReservationReportFilterDTO filter);
}
