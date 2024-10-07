package org.prgrms.devconnect.api.controller.jobpost.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import org.prgrms.devconnect.api.controller.techstack.dto.response.TechStackResponseDto;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPostTechStackMapping;
import org.prgrms.devconnect.domain.define.jobpost.entity.constant.JobType;
import org.prgrms.devconnect.domain.define.jobpost.entity.constant.Status;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record JobPostInfoResponseDto(
    Long jobPostId,
    Long postId,
    String jobPostName,
    String companyName,
    String companyAddress,
    String companyLink,
    LocalDateTime postDate,
    LocalDateTime openDate,
    LocalDateTime endDate,
    String salary,
    JobType jobType,
    Status status,
    int views,
    int likes,
    List<TechStackResponseDto> techStacks
) {

  public static JobPostInfoResponseDto from(JobPost jobPost) {

    List<TechStackResponseDto> techStackDtos = jobPost.getJobPostTechStackMappings().stream()
            .map(JobPostTechStackMapping::getTechStack)
            .map(TechStackResponseDto::from)
            .toList();

    return JobPostInfoResponseDto.builder()
            .jobPostId(jobPost.getJobPostId())
            .postId(jobPost.getPostId())
            .jobPostName(jobPost.getJobPostName())
            .companyName(jobPost.getCompanyName())
            .companyAddress(jobPost.getCompanyAddress())
            .companyLink(jobPost.getCompanyLink())
            .postDate(jobPost.getPostDate())
            .openDate(jobPost.getOpenDate())
            .endDate(jobPost.getEndDate())
            .salary(jobPost.getSalary())
            .jobType(jobPost.getJobType())
            .status(jobPost.getStatus())
            .views(jobPost.getViews())
            .likes(jobPost.getLikes())
            .techStacks(techStackDtos)
            .build();
  }

}
