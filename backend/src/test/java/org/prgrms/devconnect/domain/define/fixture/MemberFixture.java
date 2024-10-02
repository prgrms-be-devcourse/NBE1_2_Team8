package org.prgrms.devconnect.domain.define.fixture;

import java.util.ArrayList;
import java.util.List;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberCreateRequestDto;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberLoginRequestDto;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberUpdateRequestDto;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.entity.MemberTechStackMapping;
import org.prgrms.devconnect.domain.define.member.entity.constant.Interest;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;

public class MemberFixture {

  public static Member createMember(TechStack techStack) {
    List<MemberTechStackMapping> memberTechStacks = new ArrayList<>();
    memberTechStacks.add(MemberTechStackMapping.builder().techStack(techStack).build());
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
        .memberTechStacks(memberTechStacks)
        .build();
  }

  public static Member createMember(String email) {
    return Member.builder()
        .email(email)
        .password("password123")
        .nickname("testUser")
        .job("testJob")
        .affiliation("Test Company")
        .career(5)
        .selfIntroduction("새로운 tech에 관심 많은 열정적인 개발자!")
        .blogLink("https://testuserblog.com")
        .githubLink("https://github.com/testUser")
        .interest(Interest.JOBPOST)
        .memberTechStacks(List.of())
        .build();
  }

  public static Member createMember(String email, String password) {
    return Member.builder()
        .email(email)
        .password(password)
        .nickname("testUser")
        .job("testJob")
        .affiliation("Test Company")
        .career(5)
        .selfIntroduction("새로운 tech에 관심 많은 열정적인 개발자!")
        .blogLink("https://testuserblog.com")
        .githubLink("https://github.com/testUser")
        .interest(Interest.JOBPOST)
        .memberTechStacks(List.of())
        .build();
  }

  public static Member createMember(String email, String password, String nickname) {
    return Member.builder()
        .email(email)
        .password(password)
        .nickname(nickname)
        .job("testJob")
        .affiliation("Test Company")
        .career(5)
        .selfIntroduction("새로운 tech에 관심 많은 열정적인 개발자!")
        .blogLink("https://testuserblog.com")
        .githubLink("https://github.com/testUser")
        .interest(Interest.JOBPOST)
        .memberTechStacks(List.of())
        .build();
  }

  // MemberLoginReqeustDto
  public static MemberLoginRequestDto createMemberLoginRequestDto(String email, String password) {
    return MemberLoginRequestDto.builder()
        .email(email)
        .password(password)
        .build();
  }

  // MemberCreateRequestDto
  public static MemberCreateRequestDto createMemberCreateRequestDto() {
    return MemberCreateRequestDto.builder()
        .email("test@email.com")
        .password("password")
        .nickname("nickname")
        .job("job")
        .affiliation("programmers")
        .career(0)
        .selfIntroduction("self Introduction")
        .githubLink("github.com/test")
        .blogLink("blog.com/test")
        .interest(Interest.STUDY)
        .techStackIds(List.of(1L, 2L))
        .build();
  }

  public static MemberCreateRequestDto createMemberCreateRequestDto(String email) {
    return MemberCreateRequestDto.builder()
        .email(email)
        .password("password")
        .nickname("nickname")
        .job("job")
        .affiliation("programmers")
        .career(0)
        .selfIntroduction("self Introduction")
        .githubLink("github.com/test")
        .blogLink("blog.com/test")
        .interest(Interest.STUDY)
        .techStackIds(List.of(1L, 2L))
        .build();
  }

  //MemberUpdateRequestDto
  public static MemberUpdateRequestDto createMemberUpdateRequestDto(String nickname) {
    return MemberUpdateRequestDto.builder()
        .nickname(nickname)
        .job("job")
        .affiliation("programmers")
        .career(0)
        .selfIntroduction("self Introduction")
        .githubLink("github.com/test")
        .blogLink("blog.com/test")
        .interest(Interest.STUDY)
        .build();
  }
}