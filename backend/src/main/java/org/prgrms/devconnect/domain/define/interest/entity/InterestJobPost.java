package org.prgrms.devconnect.domain.define.interest.entity;

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
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.member.entity.Member;

@Entity
@Table(name = "interest_job_post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestJobPost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "interest_job_post_id")
  private Long interestJobPostId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job_post_id", nullable = false)
  private JobPost jobPost;

  @Builder
  public InterestJobPost(Member member, JobPost jobPost) {
    this.member = member;
    this.jobPost = jobPost;
  }
}
