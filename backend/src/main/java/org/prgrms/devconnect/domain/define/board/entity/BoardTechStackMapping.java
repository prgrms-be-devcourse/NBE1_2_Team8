package org.prgrms.devconnect.domain.define.board.entity;

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
@Table(name = "board_tech_stack_mapping")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardTechStackMapping {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id", nullable = false)
  private Board board;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tech_stack_id", nullable = false)
  private TechStack techStack;

  // Builder 패턴 사용
  @Builder
  public BoardTechStackMapping(TechStack techStack) {
    this.techStack = techStack;
  }

  // 연관관계 편의 메소드
  public void assignBoard(Board board) {
    this.board = board;
  }

  // 연관관계 편의 메소드
  public void assignTechStack(TechStack techStack) {
    this.techStack = techStack;
  }
}
