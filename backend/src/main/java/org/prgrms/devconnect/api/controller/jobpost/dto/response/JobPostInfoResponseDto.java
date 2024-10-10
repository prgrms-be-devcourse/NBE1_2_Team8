package org.prgrms.devconnect.api.controller.jobpost.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "채용 공고 ID", example = "123")
    Long jobPostId,

    @Schema(description = "게시물 ID", example = "456")
    Long postId,

    @Schema(description = "채용 공고 이름", example = "Backend Developer")
    String jobPostName,

    @Schema(description = "회사 이름", example = "ABC Corp")
    String companyName,

    @Schema(description = "회사 주소", example = "서울시 강남구 테헤란로 123")
    String companyAddress,

    @Schema(description = "회사 링크", example = "http://example.com")
    String companyLink,

    @Schema(description = "게시 날짜", example = "2024-10-04T10:15:30")
    LocalDateTime postDate,

    @Schema(description = "공고 시작 날짜", example = "2024-10-05T10:15:30")
    LocalDateTime openDate,

    @Schema(description = "공고 종료 날짜", example = "2024-10-30T10:15:30")
    LocalDateTime endDate,

    @Schema(description = "급여", example = "회사 내규에 따름")
    String salary,

    @Schema(description = "직무 타입", example = "REGULAR")
    JobType jobType,

    @Schema(description = "상태", example = "RECRUITING")
    Status status,

    @Schema(description = "조회수", example = "100")
    int views,

    @Schema(description = "좋아요 수", example = "100")
    int likes,

    @Schema(description = "직무 기술 스택 목록")
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
