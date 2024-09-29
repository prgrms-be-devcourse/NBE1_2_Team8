package org.prgrms.devconnect.domain.define.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;

@Entity
@Table(name = "member_tech_stack_mapping")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberTechStackMapping {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tech_stack_id", nullable = false)
  private TechStack techStack;

  @Builder
  public MemberTechStackMapping(TechStack techStack) {
    this.techStack = techStack;
  }

  public void assignMember(Member member) {
    this.member = member;
  }

}
