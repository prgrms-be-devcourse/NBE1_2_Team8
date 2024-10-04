package org.prgrms.devconnect.api.controller.jobpost.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.jobpost.entity.constant.JobType;
import org.prgrms.devconnect.domain.define.jobpost.entity.constant.Status;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record JobPostInfoResponseDto(
    Long jobPostId,
    String jobPostName,
    String companyName,
    String companyLink,
    LocalDateTime postDate,
    LocalDateTime openDate,
    LocalDateTime endDate,
    String salary,
    JobType jobType,
    Status status,
    int views,
    int likes
) {

  public static JobPostInfoResponseDto from(JobPost jobPost) {
    return JobPostInfoResponseDto.builder()
        .jobPostId(jobPost.getJobPostId())
        .jobPostName(jobPost.getJobPostName())
        .companyName(jobPost.getCompanyName())
        .companyLink(jobPost.getCompanyLink())
        .postDate(jobPost.getPostDate())
        .openDate(jobPost.getOpenDate())
        .endDate(jobPost.getEndDate())
        .salary(jobPost.getSalary())
        .jobType(jobPost.getJobType())
        .status(jobPost.getStatus())
        .views(jobPost.getViews())
        .likes(jobPost.getLikes())
        .build();
  }

}
