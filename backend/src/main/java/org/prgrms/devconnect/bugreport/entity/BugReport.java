package org.prgrms.devconnect.bugreport.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.bugreport.entity.constant.BugType;
import org.prgrms.devconnect.common.audit.Timestamp;
import org.prgrms.devconnect.member.entity.Member;

@Entity
@Table(name = "bug_report")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BugReport extends Timestamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bug_report_id")
  private Long bugReportId;

  @ManyToOne
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @Column(name = "related_url", length = 500)
  private String relatedUrl;

  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @Enumerated(value = EnumType.STRING)
  private BugType bugType;
}
