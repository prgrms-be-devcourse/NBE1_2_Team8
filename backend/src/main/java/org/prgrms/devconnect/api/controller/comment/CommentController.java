package org.prgrms.devconnect.api.controller.comment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.comment.dto.request.CommentCreateRequestDto;
import org.prgrms.devconnect.api.controller.comment.dto.response.CommentResponseDto;
import org.prgrms.devconnect.api.service.comment.CommentCommandService;
import org.prgrms.devconnect.api.service.comment.CommentQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
  private final CommentCommandService commentCommandService;
  private final CommentQueryService commentQueryService;

  @PostMapping
  public ResponseEntity<Void> createComment(@RequestBody @Valid CommentCreateRequestDto commentCreateRequestDto) {
    commentCommandService.createComment(commentCreateRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{boardId}")
  public ResponseEntity<Page<CommentResponseDto>> getComments(@PathVariable Long boardId, Pageable pageable) {
    Page<CommentResponseDto>comments= commentCommandService.getCommentsByBoardId(boardId,pageable);
    return ResponseEntity.ok(comments);
  }
}
