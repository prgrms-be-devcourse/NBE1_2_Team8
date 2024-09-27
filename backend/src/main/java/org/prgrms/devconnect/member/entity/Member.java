package org.prgrms.devconnect.member.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.chatting.entity.ChatParticipation;
import org.prgrms.devconnect.common.audit.Timestamp;
import org.prgrms.devconnect.member.entity.constant.Interest;

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

  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
  @JsonManagedReference
  List<ChatParticipation> chattinglist = new ArrayList<>();

  public void addChattings(ChatParticipation chatParticipation){
    chattinglist.add(chatParticipation);
    chatParticipation.setMember(this);
  }

  @Builder
  public Member(String email, String password, String nickname, String affiliation, int career, String selfIntroduction, String blogLink, String githubLink, Interest interest) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.affiliation = affiliation;
    this.career = career;
    this.selfIntroduction = selfIntroduction;
    this.blogLink = blogLink;
    this.githubLink = githubLink;
    this.interest = interest;
  }
}
