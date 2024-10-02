package org.prgrms.devconnect.domain.define.jobpost.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;

@Entity
@Table(name = "job_post_tech_stack_mapping")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobPostTechStackMapping {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job_post_id", nullable = false)
  private JobPost jobPost;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "tech_stack_id", nullable = false)
  private TechStack techStack;

  @Builder
  public JobPostTechStackMapping(JobPost jobPost, TechStack techStack) {
    this.jobPost = jobPost;
    this.techStack = techStack;
  }
}
