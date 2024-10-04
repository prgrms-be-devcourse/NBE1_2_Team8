package org.prgrms.devconnect.domain.define.alarm.repository;


import java.util.List;
import java.util.Optional;
import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
  List<Alarm> findAllByMember(Member member);
  void deleteAllByMemberMemberId(Long memberId);
  void deleteByAlarmIdAndMemberMemberId(Long alarmId, Long memberId);
  Optional<Alarm> findByAlarmIdAndMemberMemberId(Long alarmId, Long memberId);
}
