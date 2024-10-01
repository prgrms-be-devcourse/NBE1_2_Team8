package org.prgrms.devconnect.domain.define.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.domain.define.Timestamp;
import org.prgrms.devconnect.domain.define.member.entity.Member;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentId;

  @Column(length = 500)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id", nullable = false)
  private Board board;

  @ManyToOne
  @JoinColumn(name = "parent_id", nullable = true)
  private Comment parent;

  @OneToMany(mappedBy = "parent")
  private List<Comment> children;

  @Builder
  public Comment(Long commentId, String content, Member member, Comment parent) {
    this.commentId = commentId;
    this.content = content;
    this.member = member;
    this.parent = parent;
  }

  // 연관관계 편의 메소드
  public void assignBoard(Board board) {
    this.board = board;
  }

  public void addChildComment(Comment comment){
    children.add(comment);
    comment.parent = this;
  }
}
