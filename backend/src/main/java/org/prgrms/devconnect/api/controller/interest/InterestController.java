package org.prgrms.devconnect.api.controller.interest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.interest.dto.request.InterestBoardRequestDto;
import org.prgrms.devconnect.api.controller.interest.dto.request.InterestJobPostRequestDto;
import org.prgrms.devconnect.api.controller.interest.dto.response.InterestResponseDto;
import org.prgrms.devconnect.api.service.interest.InterestCommandService;
import org.prgrms.devconnect.api.service.interest.InterestQueryService;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

  @GetMapping()
  public ResponseEntity<InterestResponseDto> getInterestBoards(
      @AuthenticationPrincipal Member member) {
    InterestResponseDto responseDto = interestQueryService.getInterestsByMemberId(
        member.getMemberId());
    return ResponseEntity.ok(responseDto);
  }

  @PostMapping("/boards")
  public ResponseEntity<Void> addInterestBoard(
      @Valid @RequestBody InterestBoardRequestDto requestDto,
      @AuthenticationPrincipal Member member) {
    interestCommandService.addInterestBoard(requestDto, member.getMemberId());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/boards/{boardId}")
  public ResponseEntity<Void> removeInterestBoard(@AuthenticationPrincipal Member member,
      @PathVariable Long boardId) {
    interestCommandService.removeInterestBoard(member.getMemberId(), boardId);

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/job-posts")
  public ResponseEntity<Void> addInterestJob(
      @Valid @RequestBody InterestJobPostRequestDto requestDto,
      @AuthenticationPrincipal Member member) {
    interestCommandService.addInterestJobPost(requestDto, member.getMemberId());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/job-posts/{jobPostId}")
  public ResponseEntity<Void> removeInterestJobPost(@AuthenticationPrincipal Member member,
      @PathVariable Long jobPostId) {
    interestCommandService.removeInterestJobPost(member.getMemberId(), jobPostId);
    return ResponseEntity.noContent().build();
  }

}
