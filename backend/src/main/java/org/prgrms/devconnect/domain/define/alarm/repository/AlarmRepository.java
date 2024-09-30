package org.prgrms.devconnect.domain.define.alarm.repository;


import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
