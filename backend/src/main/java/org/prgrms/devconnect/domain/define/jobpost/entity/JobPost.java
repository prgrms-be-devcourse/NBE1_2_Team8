package org.prgrms.devconnect.domain.define.jobpost.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.domain.define.jobpost.entity.constant.JobType;
import org.prgrms.devconnect.domain.define.jobpost.entity.constant.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobPost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "job_post_id")
  private Long jobPostId;

  @Column(name = "post_id")
  private Long postId;

  @Column(name = "job_post_name")
  private String jobPostName;

  @Column(name = "job_post_link")
  private String jobPostLink;

  @Column(name = "company_name")
  private String companyName;

  @Column(name = "company_link")
  private String companyLink;

  @Column(name = "company_address")
  private String companyAddress;

  @Column(name = "post_date")
  private LocalDateTime postDate;

  @Column(name = "open_date")
  private LocalDateTime openDate;

  @Column(name = "end_date")
  private LocalDateTime endDate;

  @Column(name = "experience_level")
  private String experienceLevel;

  @Column(name = "required_education")
  private String requiredEducation;

  @Column(name = "salary")
  private String salary;

  @Column(name = "job_type")
  @Enumerated(value = EnumType.STRING)
  private JobType jobType;

  @Column(name = "status")
  @Enumerated(value = EnumType.STRING)
  private Status status;

  @Column(name = "likes")
  private int likes;

  @Column(name = "views")
  private int views;

  @OneToMany(mappedBy = "jobPost", cascade = CascadeType.PERSIST)
  private List<JobPostTechStackMapping> jobPostTechStackMappings = new ArrayList<>();

  @Builder
  public JobPost(Long postId, String jobPostName, String jobPostLink, String companyName,
                 String companyLink, String companyAddress, LocalDateTime postDate,
                 LocalDateTime openDate, LocalDateTime endDate, String experienceLevel,
                 String requiredEducation, String salary, JobType jobType, Status status,
                 int likes, int views, List<JobPostTechStackMapping> jobPostTechStackMappings) {
    this.postId = postId;
    this.jobPostName = jobPostName;
    this.jobPostLink = jobPostLink;
    this.companyName = companyName;
    this.companyLink = companyLink;
    this.companyAddress = companyAddress;
    this.postDate = postDate;
    this.openDate = openDate;
    this.endDate = endDate;
    this.experienceLevel = experienceLevel;
    this.requiredEducation = requiredEducation;
    this.salary = salary;
    this.jobType = jobType;
    this.status = status;
    this.likes = likes;
    this.views = views;
    this.jobPostTechStackMappings = jobPostTechStackMappings != null ? jobPostTechStackMappings : new ArrayList<>();
  }

  public void addTechStackMapping(JobPostTechStackMapping mapping) {
    this.jobPostTechStackMappings.add(mapping);
  }

  // 공고 상태 변경
  public void updateStatus(Status status) {
    this.status = status;
  }

  // 조회수 1 증가
  public void increaseViews() {
    this.views += 1;
  }

  // 좋아요수 1 증가
  public void increaseLikes() {
    this.likes += 1;
  }

  // 좋아요수 1 감소
  public void decreaseLikes() {
    this.likes -= 1;
  }
}