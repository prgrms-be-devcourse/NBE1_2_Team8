package org.prgrms.devconnect.api.controller.comment;

import static org.springframework.http.HttpStatus.OK;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.comment.dto.request.CommentCreateRequestDto;
import org.prgrms.devconnect.api.controller.comment.dto.request.CommentUpdateRequestDto;
import org.prgrms.devconnect.api.controller.comment.dto.response.CommentResponseDto;
import org.prgrms.devconnect.api.service.comment.CommentCommandService;
import org.prgrms.devconnect.api.service.comment.CommentQueryService;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentCommandService commentCommandService;
  private final CommentQueryService commentQueryService;

  @PostMapping
  public ResponseEntity<Void> createComment(@AuthenticationPrincipal Member member,
      @RequestBody @Valid CommentCreateRequestDto commentCreateRequestDto) {
    commentCommandService.createComment(commentCreateRequestDto, member.getMemberId());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{boardId}")
  public ResponseEntity<Page<CommentResponseDto>> getComments(@PathVariable Long boardId,
      Pageable pageable) {
    Page<CommentResponseDto> comments = commentQueryService.getCommentsByBoardId(boardId, pageable);
    return ResponseEntity.status(OK).body(comments);
  }

  @PutMapping("/{commentId}")
  public ResponseEntity<Void> updateComment(@PathVariable Long commentId,
      @RequestBody @Valid CommentUpdateRequestDto commentUpdateRequestDto) {
    commentCommandService.updateComment(commentId, commentUpdateRequestDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
    commentCommandService.deleteComment(commentId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
