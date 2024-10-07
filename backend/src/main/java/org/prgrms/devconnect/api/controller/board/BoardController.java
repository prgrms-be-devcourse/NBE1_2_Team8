package org.prgrms.devconnect.api.controller.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.board.dto.BoardFilterDto;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardCreateRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardUpdateRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.response.BoardResponseDto;
import org.prgrms.devconnect.api.service.board.BoardCommandService;
import org.prgrms.devconnect.api.service.board.BoardQueryService;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardCategory;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.board.entity.constant.ProgressWay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping("/{boardId}")
  public ResponseEntity<BoardResponseDto>getBoardById(@PathVariable Long boardId){
    // 게시물을 조회하면서 조회수 증가를 동시에 처리
    BoardResponseDto boardResponse = boardQueryService.getBoardById(boardId);
    boardCommandService.increaseViews(boardId); // 조회수 증가 호출
    return ResponseEntity.status(HttpStatus.OK).body(boardResponse);
  }

  @GetMapping
  public ResponseEntity<Page<BoardResponseDto>> getAllBoards(Pageable pageable){
    Page<BoardResponseDto> boards = boardQueryService.getAllBoards(pageable);
    return ResponseEntity.status(HttpStatus.OK).body(boards);
  }

  @GetMapping("/filter")
  public ResponseEntity<Page<BoardResponseDto>> getBoardsByFilter(
          @RequestParam(required = false) BoardCategory category,
          @RequestParam(required = false) BoardStatus status,
          @RequestParam(required = false) List<Long> techStackIds,
          @RequestParam(required = false) ProgressWay progressWay,
          Pageable pageable
  ){
    BoardFilterDto filterDto =  BoardFilterDto.builder()
            .category(category)
            .status(status)
            .techStackIds(techStackIds)
            .progressWay(progressWay)
            .build();
    Page<BoardResponseDto> boards = boardQueryService.getBoardsByFilter(pageable, filterDto);
    return ResponseEntity.status(HttpStatus.OK).body(boards);
  }

  @GetMapping("/popular")
  public ResponseEntity<List<BoardResponseDto>> getPopularBoardsThisWeek() {
    List<BoardResponseDto> popularBoards = boardQueryService.getTop10PopularBoardsThisWeek();
    return ResponseEntity.status(HttpStatus.OK).body(popularBoards);
  }

  @GetMapping("/{memberId}/interests")
  public ResponseEntity<List<BoardResponseDto>>getBoardsByMemberInterests(@PathVariable Long memberId){
    List<BoardResponseDto> boards = boardQueryService.getBoardsByMemberInterests(memberId);
    return ResponseEntity.status(HttpStatus.OK).body(boards);
  }
}
