package org.prgrms.devconnect.api.controller.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardCreateRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardUpdateRequestDto;
import org.prgrms.devconnect.api.service.board.BoardCommandService;
import org.prgrms.devconnect.api.service.board.BoardQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController {

  private final BoardCommandService boardCommandService;
  private final BoardQueryService boardQueryService;

  @PostMapping
  public ResponseEntity<Void> createBoard(@RequestBody @Valid BoardCreateRequestDto boardCreateRequestDto) {
    boardCommandService.createBoard(boardCreateRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/{boardId}")
  public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
    boardCommandService.deleteBoard(boardId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PutMapping("/{boardId}")
  public ResponseEntity<Void> updateBoard(
          @PathVariable Long boardId,
          @RequestBody @Valid BoardUpdateRequestDto boardUpdateRequestDto) {
    boardCommandService.updateBoard(boardId,boardUpdateRequestDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PutMapping("/{boardId}/close")
  public ResponseEntity<Void> closeBoard(
          @PathVariable Long boardId) {
    boardCommandService.closeBoardManually(boardId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }


}
