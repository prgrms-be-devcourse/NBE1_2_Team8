package org.prgrms.devconnect.api.controller.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardCreateRequestDto;
import org.prgrms.devconnect.api.service.board.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @PostMapping
  public ResponseEntity<Void> createBoard(@RequestBody @Valid BoardCreateRequestDto boardCreateRequestDto) {
    boardService.createBoard(boardCreateRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/{boardId}")
  public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
    boardService.deleteBoard(boardId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PutMapping("/{boardId}")
  public ResponseEntity<Void> updateBoard(
          @PathVariable Long boardId,
          @RequestBody @Valid BoardCreateRequestDto boardCreateRequestDto) {
    boardService.updateBoard(boardId,boardCreateRequestDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
