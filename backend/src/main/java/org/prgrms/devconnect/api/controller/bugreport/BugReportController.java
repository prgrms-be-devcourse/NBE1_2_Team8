package org.prgrms.devconnect.api.controller.bugreport;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.bugreport.dto.request.BugReportRequest;
import org.prgrms.devconnect.api.controller.bugreport.dto.request.BugReportUpdateRequest;
import org.prgrms.devconnect.api.controller.bugreport.dto.response.BugReportResponse;
import org.prgrms.devconnect.api.service.bugreport.BugReportCommendService;
import org.prgrms.devconnect.api.service.bugreport.BugReportQueryService;
import org.prgrms.devconnect.domain.define.bugreport.entity.BugReport;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bug-report")
@Tag(name = "버그리포트 API", description = "버그 관련 신고를 하는 게시판")
public class BugReportController {

  private final BugReportCommendService bugReportCommendService;
  private final BugReportQueryService bugReportQueryService;

  // 버그 전체 조회

  @Operation(summary = "버그 리포트 생성", description = "새로운 버그 리포트를 생성합니다.")
  @ApiResponse(responseCode = "201", description = "버그 리포트가 성공적으로 생성되었습니다.",
      content = @Content(schema = @Schema(implementation = BugReport.class)))
  @PostMapping
  public ResponseEntity<BugReportResponse> createBugReport(@RequestBody @Valid BugReportRequest dto,
      @AuthenticationPrincipal Member member) {
    BugReportResponse response = bugReportCommendService.createBugReport(member.getMemberId(),
        dto.url(),
        dto.content(),
        dto.bugType());

    return ResponseEntity.status(CREATED).body(response);
  }

  @Operation(summary = "모든 버그 리포트 조회", description = "모든 버그 리포트를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "버그 리포트 목록을 성공적으로 반환합니다.",
      content = @Content(schema = @Schema(implementation = BugReportResponse.class)))
  @ApiResponse(responseCode = "204", description = "버그 리포트가 없습니다.")
  @GetMapping
  public ResponseEntity<List<BugReportResponse>> getAllBugReports() {
    List<BugReportResponse> results = bugReportQueryService.getAllBugReport();

    if (results.isEmpty()) {
      return ResponseEntity.status(NO_CONTENT).build();
    }

    return ResponseEntity.status(OK).body(results);
  }

  @Operation(summary = "특정 버그 리포트 조회", description = "버그 리포트 ID로 버그 리포트의 상세 정보를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "버그 리포트 상세 정보를 성공적으로 반환합니다.",
      content = @Content(schema = @Schema(implementation = BugReport.class)))
  @GetMapping("/{reportId}")
  public ResponseEntity<BugReportResponse> getBugReportById(
      @PathVariable("reportId") Long reportId) {
    BugReport bugReport = bugReportQueryService.getBugReport(reportId);
    BugReportResponse response = BugReportResponse.from(bugReport);
    return ResponseEntity.status(OK).body(response);
  }

  @Operation(summary = "버그 리포트 수정", description = "특정 버그 리포트를 수정합니다.")
  @ApiResponse(responseCode = "200", description = "버그 리포트가 성공적으로 수정되었습니다.")
  @Parameter(name = "reportId", description = "수정할 버그 리포트의 ID", required = true)
  @PutMapping("/{reportId}")
  public ResponseEntity<BugReportResponse> updateBugReport(@PathVariable("reportId") Long reportId,
      @RequestBody @Valid BugReportUpdateRequest dto) {
    BugReportResponse response = bugReportCommendService.updateBugReport(reportId, dto.url(),
        dto.content(), dto.bugType());

    return ResponseEntity.status(OK).body(response);
  }

  @Operation(summary = "버그 리포트 삭제", description = "특정 버그 리포트를 삭제합니다.")
  @ApiResponse(responseCode = "204", description = "버그 리포트가 성공적으로 삭제되었습니다.")
  @DeleteMapping("/{reportId}")
  public ResponseEntity<Object> deleteBugReport(@PathVariable("reportId") Long reportId) {
    System.out.println("reportId = " + reportId);
    bugReportCommendService.removeBug(reportId);

    return ResponseEntity.status(NO_CONTENT).build();
  }

  @Operation(summary = "특정 사용자의 모든 버그 리포트 조회", description = "특정 사용자의 모든 버그 리포트를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "해당 사용자의 버그 리포트 목록을 성공적으로 반환합니다.",
      content = @Content(schema = @Schema(implementation = BugReportResponse.class)))
  @ApiResponse(responseCode = "204", description = "해당 사용자의 버그 리포트가 없습니다.")
  @GetMapping("/member")
  public ResponseEntity<List<BugReportResponse>> getAllBugReportsByMemberId(
      @AuthenticationPrincipal Member member) {
    List<BugReportResponse> results = bugReportQueryService.getAllBugReportByMemberId(
        member.getMemberId());

    if (results.isEmpty()) {
      return ResponseEntity.status(NO_CONTENT).build();
    }

    return ResponseEntity.status(OK).body(results);
  }
}
