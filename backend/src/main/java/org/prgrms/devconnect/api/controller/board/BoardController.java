package org.prgrms.devconnect.api.controller.board;

import org.prgrms.devconnect.api.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/boards")
public class BoardController {
  @Autowired
  private BoardService boardService;

  @PostMapping
  public ResponseEntity<Long> createBoard(@RequestBody BoardCreateDTO boardCreateDTO) {
    Long boardId = boardService.createBoard(boardCreateDTO);
    // 201 Created와 함께 생성된 Board ID를 반환
    return ResponseEntity.status(HttpStatus.CREATED).body(boardId);
  }
}
