package org.prgrms.devconnect.api.controller.board;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardCreateRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardUpdateRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.response.BoardResponseDto;
import org.prgrms.devconnect.api.service.board.BoardCommandService;
import org.prgrms.devconnect.api.service.board.BoardQueryService;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardCategory;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.board.entity.constant.ProgressWay;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
@Tag(name = "게시판 API", description = "게시물 관련 기능을 제공하는 API")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
})
public class BoardController {

  private final BoardCommandService boardCommandService;
  private final BoardQueryService boardQueryService;
  
  @PostMapping
  @Operation(summary = "게시물 생성", description = "새로운 게시물을 생성합니다.")
  @ApiResponses({
          @ApiResponse(responseCode = "201", description = "게시물 생성 성공"),
          @ApiResponse(responseCode = "404", description = "엔티티 NOT FOUND")
  })
  public ResponseEntity<Void> createBoard(
      @RequestBody @Valid BoardCreateRequestDto boardCreateRequestDto,
      @AuthenticationPrincipal Member member) {
    boardCommandService.createBoard(boardCreateRequestDto, member.getMemberId());

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/{boardId}")
  @Operation(summary = "게시물 삭제", description = "특정 게시물을 삭제합니다.",parameters={
          @Parameter(name="boardId", description = "게시물 ID", required = true, example = "1")
  })
  @ApiResponses({
          @ApiResponse(responseCode = "204", description = "게시물 삭제 성공"),
          @ApiResponse(responseCode = "404", description = "엔티티 NOT FOUND")
  })
  public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
    boardCommandService.deleteBoard(boardId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }


  @Operation(summary = "게시물 수정", description = "게시물 ID를 기반으로 게시물을 수정합니다.")
  @PutMapping("/{boardId}")
  public ResponseEntity<Void> updateBoard(@PathVariable Long boardId,
      @RequestBody @Valid BoardUpdateRequestDto boardUpdateRequestDto) {
    boardCommandService.updateBoard(boardId, boardUpdateRequestDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }


  @PutMapping("/{boardId}/close")
  @Operation(summary = "게시물을 마감", description = "특정 게시물을 수동으로 마감합니다.", parameters = {
          @Parameter(name = "boardId", description = "게시물 ID", required = true, example = "1")
  })
  @ApiResponses(value={
          @ApiResponse(responseCode = "200", description = "게시물 마감 성공"),
          @ApiResponse(responseCode = "403", description = "이미 마감된 게시물입니다."),
          @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물 또는 삭제된 게시물입니다.")
  })
  public ResponseEntity<Void> closeBoard(@PathVariable Long boardId) {
    boardCommandService.closeBoardManually(boardId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }


  @GetMapping("/{boardId}")
  @Operation(summary = "단일 게시물 조회", description = "특정 게시물의 조회수를 증가시키고 게시물 정보를 반환합니다.", parameters = {
          @Parameter(name = "boardId", description = "게시물 ID", required = true, example = "1")
  })
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "게시물 조회 성공"),
          @ApiResponse(responseCode = "404", description = "엔티티 NOT FOUND")
  })
  public ResponseEntity<BoardResponseDto> getBoardById(@PathVariable Long boardId) {
    // 게시물을 조회하면서 조회수 증가를 동시에 처리
    boardCommandService.increaseViews(boardId); // 조회수 증가 호출
    BoardResponseDto boardResponse = boardQueryService.getBoardById(boardId);
    return ResponseEntity.status(HttpStatus.OK).body(boardResponse);
  }

  @GetMapping
  @Operation(summary = "전체 게시물 조회", description = "모든 게시물을 페이징하여 조회합니다.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "게시물 조회 성공")
  })
  public ResponseEntity<Page<BoardResponseDto>> getAllBoards(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
    Page<BoardResponseDto> boards = boardQueryService.getAllBoards(pageable);
    return ResponseEntity.status(HttpStatus.OK).body(boards);
  }


  @GetMapping("/filter")
  @Operation(summary = "필터로 게시물 조회", description = "카테고리, 상태, 기술 스택 등의 조건으로 게시물을 필터링하여 조회합니다.",parameters = {
          @Parameter(name = "category", description = "게시물 카테고리", required = false),
          @Parameter(name = "status", description = "게시물 상태", required = false),
          @Parameter(name = "techStackIds", description = "기술 스택 ID 목록", required = false),
          @Parameter(name = "progressWay", description = "진행 방식", required = false)
  })
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "게시물 조회 성공")
  })
  public ResponseEntity<Page<BoardResponseDto>> getBoardsByFilter(
          @RequestParam(required = false) BoardCategory category,
          @RequestParam(required = false) BoardStatus status,
          @RequestParam(required = false) List<Long> techStackIds,
          @RequestParam(required = false) ProgressWay progressWay,
          @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC)Pageable pageable
  ){
    Page<BoardResponseDto> boards = boardQueryService.getBoardsByFilter(category, status, techStackIds, progressWay, pageable);
    
    return ResponseEntity.status(HttpStatus.OK).body(boards);
  }


  @GetMapping("/popular")
  @Operation(summary = "이번 주 인기 게시물 조회", description = "이번 주 조회수가 높은 10개의 게시물을 조회합니다.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "게시물 조회 성공")
  })
  public ResponseEntity<List<BoardResponseDto>> getPopularBoardsThisWeek() {
    List<BoardResponseDto> popularBoards = boardQueryService.getTop10PopularBoardsThisWeek();
    return ResponseEntity.status(HttpStatus.OK).body(popularBoards);
  }

  @GetMapping("/interests")
  @Operation(summary = "사용자 관심사 기반 게시물 조회", description = "특정 사용자의 관심사를 기반으로 추천 게시물을 조회합니다.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "게시물 조회 성공"),
          @ApiResponse(responseCode = "404", description = "엔티티 NOT FOUND")
  })
  public ResponseEntity<List<BoardResponseDto>> getBoardsByMemberInterests(
      @AuthenticationPrincipal Member member) {
    List<BoardResponseDto> boards = boardQueryService.getBoardsByMemberInterests(
        member.getMemberId());

    return ResponseEntity.status(HttpStatus.OK).body(boards);
  }


  @GetMapping("/jobpost/{jobPostId}")
  @Operation(summary = "특정 구직 공고와 연관된 게시물 조회", description = "특정 구직 공고와 연관된 게시물들을 조회합니다.", parameters = {
          @Parameter(name = "jobPostId", description = "공고 ID", required = true, example = "1")
  })
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "게시물 조회 성공"),
          @ApiResponse(responseCode = "404", description = "엔티티 NOT FOUND")
  })
  public ResponseEntity<List<BoardResponseDto>> getBoardsByJobPostId(@PathVariable Long jobPostId) {
    List<BoardResponseDto> boards = boardQueryService.getBoardsByJobPostId(jobPostId);
    return ResponseEntity.status(HttpStatus.OK).body(boards);
  }

  @GetMapping("/popular-tag")
  @Operation(summary = "인기 태그 게시물 조회", description = "이번 주에 작성되고 조회수가 500 이상인 게시물을 조회합니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "인기 태그 게시물 조회 성공")
  })
  public ResponseEntity<List<BoardResponseDto>> getBoardsWithPopularTagCondition() {
    List<BoardResponseDto> popularTaggedBoards = boardQueryService.getBoardsWithPopularTagCondition();
    return ResponseEntity.status(HttpStatus.OK).body(popularTaggedBoards);
  }


  @GetMapping("/deadline-approaching")
  @Operation(summary = "마감 임박 게시물 조회", description = "마감 2일 전 게시물을 조회합니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "마감 임박 게시물 조회 성공")
  })
  public ResponseEntity<List<BoardResponseDto>> getBoardsWithDeadlineApproaching() {
    List<BoardResponseDto> deadlineApproachingBoards = boardQueryService.getBoardsWithDeadlineApproaching();
    return ResponseEntity.status(HttpStatus.OK).body(deadlineApproachingBoards);
  }
}
