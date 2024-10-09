package org.prgrms.devconnect.api.controller.interest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.interest.dto.request.InterestBoardRequestDto;
import org.prgrms.devconnect.api.controller.interest.dto.request.InterestJobPostRequestDto;
import org.prgrms.devconnect.api.controller.interest.dto.response.InterestResponseDto;
import org.prgrms.devconnect.api.service.interest.InterestCommandService;
import org.prgrms.devconnect.api.service.interest.InterestQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/interests")
@Tag(name = "관심게시물 API", description = "관심 게시물 관련 API")
public class InterestController {

  private final InterestQueryService interestQueryService;
  private final InterestCommandService interestCommandService;

  @Operation(summary = "전체 관심 게시물 조회", description = "관심 게시물로 등록된 모든 게시물을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "관심 게시물이 성공적으로 조회되었습니다.", content = @Content(schema = @Schema(implementation = InterestResponseDto.class)))
  @GetMapping("/{memberId}")
  public ResponseEntity<InterestResponseDto> getInterestBoards(@PathVariable Long memberId) {
    InterestResponseDto responseDto = interestQueryService.getInterestsByMemberId(memberId);
    return ResponseEntity.ok(responseDto);
  }

  @Operation(summary = "관심 게시물로 등록", description = "관심 게시물로 등록합니다.")
  @ApiResponse(responseCode = "201", description = "관심 게시물 등록에 성공하였습니다.")
  @PostMapping("/boards")
  public ResponseEntity<Void> addInterestBoard(
      @Valid @RequestBody InterestBoardRequestDto requestDto) {
    interestCommandService.addInterestBoard(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "관심 게시물에서 해제", description = "관심 게시물에서 해제합니다.")
  @ApiResponse(responseCode = "204", description = "관심 게시물에서 성공적으로 삭제되었습니다.")
  @DeleteMapping("/boards/{memberId}/{boardId}")
  public ResponseEntity<Void> removeInterestBoard(@PathVariable Long memberId,
      @PathVariable Long boardId) {
    interestCommandService.removeInterestBoard(memberId, boardId);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "관심 채용공고으로 등록", description = "관심 채용공고으로 등록합니다.")
  @ApiResponse(responseCode = "201", description = "관심 채용공고 등록에 성공했습니다.")
  @PostMapping("/job-posts")
  public ResponseEntity<Void> addInterestJob(
      @Valid @RequestBody InterestJobPostRequestDto requestDto) {
    interestCommandService.addInterestJobPost(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "관심 채용공고에서 해제", description = "관심 채용공고에서 해제합니다.")
  @ApiResponse(responseCode = "204", description = "관심 채용공고에서 성공적으로 삭제되었습니다.")
  @DeleteMapping("/job-posts/{memberId}/{jobPostId}")
  public ResponseEntity<Void> removeInterestJobPost(@PathVariable Long memberId,
      @PathVariable Long jobPostId) {
    interestCommandService.removeInterestJobPost(memberId, jobPostId);

    return ResponseEntity.noContent().build();
  }

}
