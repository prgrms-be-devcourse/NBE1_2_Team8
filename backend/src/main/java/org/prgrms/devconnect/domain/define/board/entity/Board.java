package org.prgrms.devconnect.domain.define.board.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardUpdateRequestDto;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.Timestamp;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends Timestamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_id")
  private Long boardId;

  @ManyToOne
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job_post_id")
  private JobPost jobPost;

  @Column(name = "title", length = 200)
  private String title;

  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @Column(name = "category", length = 200)
  private String category;

  @Column(name = "recruit_num")
  private int recruitNum;

  @Column(name = "progress_way", length = 50)
  private String progressWay;

  @Column(name = "progress_period", length = 50)
  private String progressPeriod;

  @Column(name = "end_date")
  private LocalDateTime endDate;

  @Column(name = "likes")
  private int likes;

  @Column(name = "views")
  private int views;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "status", length = 50)
  private BoardStatus status = BoardStatus.RECRUITING;

  @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST)
  private List<BoardTechStackMapping> boardTechStacks = new ArrayList<>();



  // 연관관계 편의 메소드
  public void addTechStack(BoardTechStackMapping boardTechStack) {
    boardTechStacks.add(boardTechStack);
    boardTechStack.assignBoard(this);
  }


  //  Board 생성자
  @Builder
  public Board(Member member, JobPost jobPost, String title, String content, String category,
               int recruitNum, String progressWay, String progressPeriod, LocalDateTime endDate,
               List<BoardTechStackMapping> boardTechStacks) {
    this.member = member;
    this.jobPost = jobPost;
    this.title = title;
    this.content = content;
    this.category = category;
    this.recruitNum = recruitNum;
    this.progressWay = progressWay;
    this.progressPeriod = progressPeriod;
    this.endDate = endDate;
    if (!boardTechStacks.isEmpty()) {
      boardTechStacks.forEach(this::addTechStack);
    }
  }

  // Board의 상태를 변경하는 메소드
  public void changeStatus(BoardStatus status) {
    this.status = status;
  }

  public boolean isDeleted(){
    return this.status == BoardStatus.DELETED;
  }

  public boolean isClosed(){
    return this.status == BoardStatus.CLOSED;
  }

  // Board 정보를 업데이트하는 메소드
  public void updateFromDto(BoardUpdateRequestDto dto) {
    if (!dto.title().equals(this.title)) {
      this.title = dto.title();
    }
    if (!dto.content().equals(this.content)) {
      this.content = dto.content();
    }
    if (!dto.category().equals(this.category)) {
      this.category = dto.category();
    }
    if (dto.recruitNum() != this.recruitNum) {
      this.recruitNum = dto.recruitNum();
    }
    if (!dto.progressWay().equals(this.progressWay)) {
      this.progressWay = dto.progressWay();
    }
    if (!dto.progressPeriod().equals(this.progressPeriod)) {
      this.progressPeriod = dto.progressPeriod();
    }
    if (!dto.endDate().equals(this.endDate)) {
      this.endDate = dto.endDate();
    }


  }
}
