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

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.devconnect.api.controller.comment.dto.request.CommentUpdateRequestDto;
import org.prgrms.devconnect.api.controller.comment.dto.response.CommentResponseDto;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id", nullable = false)
  private Board board;

  @ManyToOne
  @JoinColumn(name = "parent_id", nullable = true)
  private Comment parent;

  @Column(length = 500)
  private String content;

  @Builder
  public Comment(Member member,Board board, Comment parent,String content) {
    this.member = member;
    this.board = board;
    this.parent = parent;
    this.content = content;
  }
  public boolean isRootComment(){
    return parent == null;
  }
  public CommentResponseDto toResponseDto() {
    return CommentResponseDto.builder()
            .commentId(commentId)
            .memberId(member.getMemberId())
            .author(member.getNickname())
            .content(content)
            .updatedAt(getUpdatedAt())
            .parentId(parent != null ? parent.getCommentId() : null)
            .build();
  }

  public void updateFromDto(CommentUpdateRequestDto dto) {
    if(!dto.content().equals(this.content))
    this.content= dto.content();
  }
}
