package org.prgrms.devconnect.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.common.audit.Timestamp;
import org.prgrms.devconnect.member.entity.constant.Interest;

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

  @OneToMany(mappedBy = "member")
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
      MemberTechStackMapping... memberTechStacks) {
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

    for (MemberTechStackMapping memberTechStack : memberTechStacks) {
      this.addMemberTechStack(memberTechStack);
    }
  }

  // == 연관관계 편의메서드 ==
  public void addMemberTechStack(MemberTechStackMapping memberTechStack) {
    memberTechStacks.add(memberTechStack);
    memberTechStack.setMember(this);
  }

  public boolean validatePassword(String password) {
    return this.password.equals(password);
  }
}
