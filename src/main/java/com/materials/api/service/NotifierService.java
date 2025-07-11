package com.materials.api.service;

import com.materials.api.entity.Item;
import com.materials.api.entity.Reservation;
import com.materials.api.entity.User;
import com.materials.api.utils.EmailTemplateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotifierService {
  private static final String NOTIFIER_EMAIL_SUBJECT = "Reservation Confirmation";

  private final EmailService emailService;

  public void sendEmailToConfirmReservation(User user, Item item, Reservation reservation) {
    var emailContent = EmailTemplateUtils.getReservationConfirmationEmail(user, item, reservation);
    emailService.sendEmailWithSendGrid(user.getEmail(), NOTIFIER_EMAIL_SUBJECT, emailContent);
  }
}
