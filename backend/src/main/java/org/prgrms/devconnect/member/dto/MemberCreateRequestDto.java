package org.prgrms.devconnect.member.dto;

import org.prgrms.devconnect.member.entity.Member;
import org.prgrms.devconnect.member.entity.constant.Interest;

public record MemberCreateRequestDto(
    String email,
    String password,
    String nickname,
    String job,
    String affiliation,
    int career,
    String selfIntroduction,
    String blogLink,
    String githubLink,
    Interest interest
) {

  public Member toEntity() {
    return Member.builder()
        .email(email)
        .password(password)
        .nickname(nickname)
        .job(job)
        .affiliation(affiliation)
        .career(career)
        .selfIntroduction(selfIntroduction)
        .blogLink(blogLink)
        .githubLink(githubLink)
        .interest(interest)
        .build();
  }
}
