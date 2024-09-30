package org.prgrms.devconnect.domain.define.fixture;

import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.entity.constant.Interest;

public class MemberFixture {

  public static Member createMember() {
    return Member.builder()
            .email("testUser@example.com")
            .password("password123")
            .nickname("testUser")
            .job("testJob")
            .affiliation("Test Company")
            .career(5)
            .selfIntroduction("새로운 tech에 관심 많은 열정적인 개발자!")
            .blogLink("https://testuserblog.com")
            .githubLink("https://github.com/testUser")
            .interest(Interest.JOBPOST)
            .build();
  }
}