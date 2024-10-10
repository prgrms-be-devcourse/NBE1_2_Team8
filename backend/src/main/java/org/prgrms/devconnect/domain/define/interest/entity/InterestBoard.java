package org.prgrms.devconnect.domain.define.interest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.member.entity.Member;

@Entity
@Table(name = "interest_board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestBoard {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "interest_board_id")
  private Long interestBoardId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id", nullable = false)
  private Board board;

  @Builder
  public InterestBoard(Member member, Board board) {
    this.member = member;
    this.board = board;
  }

  public boolean isUrgent() {
    LocalDate today = LocalDate.now();
    LocalDate endDate = this.board.getEndDate().toLocalDate();
    return endDate.isBefore(today.plusDays(3L));
  }
}
