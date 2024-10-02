package org.prgrms.devconnect.domain.define.alarm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.domain.define.CreateTimestamp;
import org.prgrms.devconnect.domain.define.member.entity.Member;

@Entity
@Table(name = "alarm")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends CreateTimestamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "alarm_id")
  private Long alarmId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @Column(name = "alert_text", length = 500)
  private String alertText;

  @Column(name = "related_url", length = 500)
  private String relatedUrl;

  @Column(name = "is_read")
  private boolean isRead;

  @Builder
  public Alarm(Member member, String alertText, String relatedUrl, boolean isRead) {
    this.member = member;
    this.alertText = alertText;
    this.relatedUrl = relatedUrl;
    this.isRead = isRead;
  }
}
