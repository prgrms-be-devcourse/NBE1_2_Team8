package org.prgrms.devconnect.api.service.alarm;

import groovy.util.logging.Slf4j;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EmailService {

  private static final Logger log = LoggerFactory.getLogger(EmailService.class);
  private final JavaMailSender javaMailSender;
  private final SpringTemplateEngine templateEngine;

  @Async
  public void sendEmail(Alarm alarm) {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    try {
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
      mimeMessageHelper.setTo(alarm.getMember().getEmail()); // 메일 수신자
      mimeMessageHelper.setSubject("오늘의 DevConnect에서 온 알림"); // 메일 제목
      mimeMessageHelper.setText(setContext(alarm), true); // 메일 본문 내용, HTML 여부

      javaMailSender.send(mimeMessage);

      log.info("Succeeded to send Email");
    } catch (Exception e) {
      log.info("Failed to send Email");
      throw new RuntimeException(e);
    }
  }

  //thymeleaf를 통한 html 적용
  public String setContext(Alarm alarm) {
    Context context = new Context();
    context.setVariable("alertText", alarm.getAlertText());
    context.setVariable("relatedUrl", alarm.getRelatedUrl());
    return templateEngine.process("emailForm", context);

  }
}
