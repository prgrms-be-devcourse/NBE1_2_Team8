package org.prgrms.devconnect.api.controller.techstack;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.techstack.dto.response.TechStackResponseDto;
import org.prgrms.devconnect.api.service.techstack.TechStackQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tech-stacks")
@Tag(name = "기술 스택 API", description = "기술 스택 관련 API")
public class TechStackController {

  private final TechStackQueryService techStackQueryService;

  @GetMapping
  @Operation(summary = "기술 스택 목록 조회", description = "모든 기술 스택 목록을 조회합니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "기술 스택 목록 조회 성공"),
          @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  public ResponseEntity<List<TechStackResponseDto>> getTechStacks() {
    List<TechStackResponseDto> responseDtos = techStackQueryService.getAllTechStacks();
    return ResponseEntity.ok(responseDtos);
  }
}