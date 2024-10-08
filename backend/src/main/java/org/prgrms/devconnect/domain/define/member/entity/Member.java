package org.prgrms.devconnect.domain.define.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberUpdateRequestDto;
import org.prgrms.devconnect.domain.define.Timestamp;
import org.prgrms.devconnect.domain.define.member.entity.constant.Interest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Timestamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long memberId;

  @Column(name = "email", length = 100)
  private String email;

  @Column(name = "password", length = 100)
  private String password;

  @Column(name = "nickname", length = 50)
  private String nickname;

  @Column(name = "job", length = 50)
  private String job;

  @Column(name = "affiliation", length = 100)
  private String affiliation;

  @Column(name = "career")
  private int career;

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<MemberTechStackMapping> memberTechStacks = new ArrayList<>();

  @Column(name = "self_introduction")
  private String selfIntroduction;

  @Column(name = "blog_link", length = 100)
  private String blogLink;

  @Column(name = "github_link", length = 100)
  private String githubLink;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "interest", length = 100)
  private Interest interest;

  @Builder
  public Member(String email, String password, String nickname, String job, String affiliation,
      int career, String selfIntroduction, String blogLink, String githubLink, Interest interest,
      List<MemberTechStackMapping> memberTechStacks) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.job = job;
    this.affiliation = affiliation;
    this.career = career;
    this.selfIntroduction = selfIntroduction;
    this.blogLink = blogLink;
    this.githubLink = githubLink;
    this.interest = interest;
    if (!memberTechStacks.isEmpty()) {
      memberTechStacks.forEach(this::addTechStackMapping);
    }
  }

  // == 연관관계 편의 메서드 == //
  public void addTechStackMapping(MemberTechStackMapping memberTechStack) {
    memberTechStacks.add(memberTechStack);
    memberTechStack.assignMember(this);
  }

  public boolean isValidPassword(String password) {
    return this.password.equals(password);
  }

  // UpdateDto으로 필드 값 업데이트 (기존 값과 비교해서 다르다면)
  public void updateFromDto(MemberUpdateRequestDto dto) {
    if (!dto.nickname().equals(this.nickname)) {
      this.nickname = dto.nickname();
    }
    if (!dto.job().equals(this.job)) {
      this.job = dto.job();
    }
    if (!dto.affiliation().equals(this.affiliation)) {
      this.affiliation = dto.affiliation();
    }
    if (dto.career() != this.career) {
      this.career = dto.career();
    }
    if (!dto.selfIntroduction().equals(this.selfIntroduction)) {
      this.selfIntroduction = dto.selfIntroduction();
    }
    if (!dto.blogLink().equals(this.blogLink)) {
      this.blogLink = dto.blogLink();
    }
    if (!dto.githubLink().equals(this.githubLink)) {
      this.githubLink = dto.githubLink();
    }
    if (!dto.interest().equals(this.interest)) {
      this.interest = dto.interest();
    }
  }

  public void passwordEncode(PasswordEncoder passwordEncoder) {
    this.password = passwordEncoder.encode(this.password);
  }
}
