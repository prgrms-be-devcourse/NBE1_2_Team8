package org.prgrms.devconnect.api.controller.techstack;

import java.util.List;
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
public class TechStackController {

  private final TechStackQueryService techStackQueryService;
  @GetMapping
  public ResponseEntity<List<TechStackResponseDto>> getTechStacks() {
    List<TechStackResponseDto> responseDtos = techStackQueryService.getAllTechStacks();
    return ResponseEntity.ok(responseDtos);
  }
}
