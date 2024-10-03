package org.prgrms.devconnect.api.service.alarm;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.alarm.AlarmException;
import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;
import org.prgrms.devconnect.domain.define.alarm.repository.AlarmRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmCommandService {

  private final AlarmRepository alarmRepository;

  public void createWelcomeAlarmWhenSignIn(Member member) {

    //TODO: 프론트 메인페이지 url 추가
    String mainPage = "";
    String welcomeMessage =
            member.getNickname() + "님, 만나서 반가워요👋\n 1만명의 DevConnector들이 " + member.getNickname() + "님에 대해 알고싶대요!";

    Alarm alarm = Alarm.builder()
            .member(member)
            .alertText(welcomeMessage)
            .relatedUrl(mainPage)
            .build();
    alarmRepository.save(alarm);
  }

  public Void deleteAlarmByAlarmId(Long alarmId) {
    try {
      alarmRepository.deleteById(alarmId);
      return null;
    } catch (IllegalArgumentException e) {
      throw new AlarmException(ExceptionCode.NOT_FOUND_ALARM);
    }
  }
}
