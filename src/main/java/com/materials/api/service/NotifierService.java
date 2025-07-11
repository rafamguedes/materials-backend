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

  private final EmailService emailService;

  public void sendConfirmationReservationNotification(User user, Item item, Reservation reservation) {
    var emailContent = EmailTemplateUtils.getReservationConfirmationEmail(user, item, reservation);
    emailService.sendEmailWithSendGrid(user.getEmail(), "Reservation Confirmation", emailContent);
  }

  public void sendCancellationReservationNotification(User user, Item item, Reservation reservation) {
    var emailContent = EmailTemplateUtils.getReservationCancellationEmail(user, item, reservation);
    emailService.sendEmailWithSendGrid(user.getEmail(), "Reservation Cancellation", emailContent);
  }

  public void sendStartReservationNotification(User user, Item item, Reservation reservation) {
    var emailContent = EmailTemplateUtils.getReservationConfirmationEmail(user, item, reservation);
    emailService.sendEmailWithSendGrid(user.getEmail(), "Reservation Started", emailContent);
  }

  public void sendFinishReservationNotification(User user, Item item, Reservation reservation) {
    var emailContent = EmailTemplateUtils.getReservationConfirmationEmail(user, item, reservation);
    emailService.sendEmailWithSendGrid(user.getEmail(), "Reservation Finished", emailContent);
  }
}
