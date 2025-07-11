package com.materials.api.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendGridEmailService {
  private static final String SUCCESSFULLY_SENT_EMAIL = "Email sent successfully to: ";
  private static final String FAILED_TO_SEND_EMAIL = "Failed to send email: ";
  private static final String CONTENT_TYPE = "text/plain";
  private static final String MAIL_SEND = "mail/send";

  @Value("${sendgrid.api.key}")
  private String API_KEY;

  @Value("${sendgrid.email.from}")
  private String FROM_EMAIL;

  public void sendEmail(String to, String subject, String content) {
    try {
      var from = new Email(FROM_EMAIL);
      var toEmail = new Email(to);
      var emailContent = new Content(CONTENT_TYPE, content);
      var mail = new Mail(from, subject, toEmail, emailContent);

      var sg = new SendGrid(API_KEY);
      var request = new Request();

      request.setMethod(Method.POST);
      request.setEndpoint(MAIL_SEND);
      request.setBody(mail.build());
      var response = sg.api(request);
      log.info(SUCCESSFULLY_SENT_EMAIL + "{}", response.getStatusCode());
    } catch (IOException e) {
      log.error(FAILED_TO_SEND_EMAIL + "{}", e.getMessage(), e);
      throw new RuntimeException(FAILED_TO_SEND_EMAIL + e.getMessage(), e);
    }
  }
}
