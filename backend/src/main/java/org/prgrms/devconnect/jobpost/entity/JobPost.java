package org.prgrms.devconnect.jobpost.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.jobpost.entity.constant.JobType;
import org.prgrms.devconnect.jobpost.entity.constant.Status;

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
  private String postId;

  @OneToMany(mappedBy = "jobPost")
  private List<JobPostTechStackMapping> jobPostTechStackMappings = new ArrayList<>();

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
}
