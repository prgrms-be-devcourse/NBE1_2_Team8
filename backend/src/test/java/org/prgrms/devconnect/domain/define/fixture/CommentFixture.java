package org.prgrms.devconnect.domain.define.fixture;

import org.prgrms.devconnect.api.controller.comment.dto.request.CommentCreateRequestDto;

public class CommentFixture {
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
