package org.prgrms.devconnect.api.controller.interest.dto.request;

import org.prgrms.devconnect.domain.define.interest.entity.InterestJobPost;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.member.entity.Member;

public record InterestJobPostRequestDto(
    Long memberId,
    Long jobPostId
) {

  public InterestJobPost toEntity(Member member, JobPost jobPost) {
    return InterestJobPost.builder()
        .member(member)
        .jobPost(jobPost)
        .build();
  }
}
