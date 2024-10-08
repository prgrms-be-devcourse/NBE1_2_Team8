package org.prgrms.devconnect.domain.define.alarm.repository;


import java.util.List;
import java.util.Optional;
import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
  List<Alarm> findAllByMember(Member member);
  @Query("select count(*) from Alarm a where a.member.memberId = :memberId and a.isRead = false")
  int countUnreadAlarmsByMemberId(@Param("memberId") Long memberId);
  void deleteAllByMemberMemberId(Long memberId);
  void deleteByAlarmIdAndMemberMemberId(Long alarmId, Long memberId);
  Optional<Alarm> findByAlarmIdAndMemberMemberId(Long alarmId, Long memberId);
}
