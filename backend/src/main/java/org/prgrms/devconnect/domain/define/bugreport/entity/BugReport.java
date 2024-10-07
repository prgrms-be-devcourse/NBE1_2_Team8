package org.prgrms.devconnect.domain.define.bugreport.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.domain.define.Timestamp;
import org.prgrms.devconnect.domain.define.bugreport.entity.constant.BugType;
import org.prgrms.devconnect.domain.define.member.entity.Member;

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

  @Builder
  public BugReport(Member member, String relatedUrl, String content, BugType bugType) {
    this.member = member;
    this.relatedUrl = relatedUrl;
    this.content = content;
    this.bugType = bugType;
  }

  // 연관관계 매핑
  public void setMember(Member member) {
    this.member = member;
  }

  // 보고서 업데이트
  public void updateReport(String url, String content, BugType bugType) {
    this.relatedUrl = url;
    this.content = content;
    this.bugType = bugType;
  }
}
