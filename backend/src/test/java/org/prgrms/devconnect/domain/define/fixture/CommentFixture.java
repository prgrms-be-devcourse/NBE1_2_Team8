package org.prgrms.devconnect.domain.define.fixture;

import org.prgrms.devconnect.api.controller.comment.dto.request.CommentCreateRequestDto;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.Comment;
import org.prgrms.devconnect.domain.define.member.entity.Member;

public class CommentFixture {
  public static Comment createComment(Member member, Board board,String content){
    return Comment.builder().board(board).content(content).member(member).build();
  }

  public static CommentCreateRequestDto createCommentCreateRequestDto() {
    return CommentCreateRequestDto.builder()
            .memberId(1L)
            .boardId(1L)
            .content("어떤 프로젝트인가요?")
            .build();
  }

  public static CommentCreateRequestDto createCommentCreateRequestDtoWithParentId() {
    return CommentCreateRequestDto.builder()
            .memberId(1L)
            .boardId(1L)
            .parentId(1L)
            .content("어떤 프로젝트인가요?")
            .build();
  }
}
