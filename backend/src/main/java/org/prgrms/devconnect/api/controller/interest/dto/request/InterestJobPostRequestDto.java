package org.prgrms.devconnect.api.controller.interest.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.interest.entity.InterestJobPost;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.member.entity.Member;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "관심 직업 등록 요청 정보")
public record InterestJobPostRequestDto(
        @NotNull(message = "채용공고 ID는 필수입니다.")
        @Schema(description = "채용공고 ID", example = "1")
        Long jobPostId
) {

  public InterestJobPost toEntity(Member member, JobPost jobPost) {
    return InterestJobPost.builder()
            .member(member)
            .jobPost(jobPost)
            .build();
  }
}
