package org.prgrms.devconnect.api.controller.interest.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.interest.entity.InterestJobPost;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.member.entity.Member;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record InterestJobPostRequestDto(
    Long jobPostId
) {

  public InterestJobPost toEntity(Member member, JobPost jobPost) {
    return InterestJobPost.builder()
        .member(member)
        .jobPost(jobPost)
        .build();
  }
}
