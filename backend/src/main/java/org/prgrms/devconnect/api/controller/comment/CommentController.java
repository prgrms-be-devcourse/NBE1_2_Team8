package org.prgrms.devconnect.api.controller.comment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.comment.dto.CommentCreateRequestDto;
import org.prgrms.devconnect.api.service.comment.CommentCommandService;
import org.prgrms.devconnect.api.service.comment.CommentQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
  public ResponseEntity<Void> createComment(@RequestBody @Valid CommentCreateRequestDto commentCreateRequestDto) {
    commentCommandService.createComment(commentCreateRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
