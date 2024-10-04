package org.prgrms.devconnect.api.controller.interest;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.board.dto.response.BoardInfoResponseDto;
import org.prgrms.devconnect.api.controller.interest.dto.request.InterestBoardRequestDto;
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
public class InterestController {

  private final InterestQueryService interestQueryService;
  private final InterestCommandService interestCommandService;

  @GetMapping("/{memberId}")
  public ResponseEntity<List<BoardInfoResponseDto>> getInterestBoards(@PathVariable Long memberId) {
    List<BoardInfoResponseDto> interestBoards = interestQueryService.getInterestBoardsByMemberId(memberId);
    return ResponseEntity.ok(interestBoards);
  }

  @PostMapping("/boards")
  public ResponseEntity<Void> addInterestBoard(
      @Valid @RequestBody InterestBoardRequestDto requestDto) {
    interestCommandService.addInterestBoard(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/boards/{memberId}/{boardId}")
  public ResponseEntity<Void> removeInterestBoard(@PathVariable Long memberId,
      @PathVariable Long boardId) {
    interestCommandService.removeInterestBoard(memberId, boardId);

    return ResponseEntity.noContent().build();
  }
}
