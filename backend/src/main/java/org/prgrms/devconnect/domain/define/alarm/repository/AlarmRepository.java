package org.prgrms.devconnect.domain.define.alarm.repository;


import java.util.List;
import java.util.Optional;
import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
  List<Alarm> findAllByMember(Member member);
  void deleteByAlarmIdAndMemberMemberId(Long alarmId, Long ㅖ);
  Optional<Alarm> findByAlarmIdAndMemberId(Long alarmId, Long memberId);
}
