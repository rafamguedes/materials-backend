package com.materials.api.service.report;

import com.materials.api.controller.dto.ReservationReportFilterDTO;
import com.materials.api.entity.Reservation;
import com.materials.api.repository.ReservationRepository;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationReportService {

  private final ReservationRepository reservationRepository;
  private final ReportService reportService;

  public byte[] generateCancelledReservationsReport(ReservationReportFilterDTO filter) {
    List<Reservation> reservationList = reservationRepository.findCancelledReservations(filter);
    if (reservationList.isEmpty()) {
        List.of();
    }
    Map<String, Object> data = Map.of("reservations", reservationList);

    return reportService.generateReport("reports/reservations_report", data);
  }
}
