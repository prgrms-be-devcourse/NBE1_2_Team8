package org.prgrms.devconnect.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.common.audit.Timestamp;
import org.prgrms.devconnect.jobpost.entity.JobPost;
import org.prgrms.devconnect.member.entity.Member;

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

  @OneToMany(mappedBy = "board")
  private List<BoardTechStackMapping> boardTechStacks = new ArrayList<>();

}
