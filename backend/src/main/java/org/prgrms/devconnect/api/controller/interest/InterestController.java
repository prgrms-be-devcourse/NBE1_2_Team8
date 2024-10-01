package org.prgrms.devconnect.api.controller.interest;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.board.dto.response.BoardInfoResponseDto;
import org.prgrms.devconnect.api.service.interest.InterestQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/interests")
public class InterestController {

  private final InterestQueryService interestQueryService;

  @GetMapping("/{memberId}")
  public ResponseEntity<List<BoardInfoResponseDto>> getInterestBoards(@PathVariable Long memberId) {
    List<BoardInfoResponseDto> interestBoards = interestQueryService.getInterestBoards(memberId);
    return ResponseEntity.ok(interestBoards);
  }
}
