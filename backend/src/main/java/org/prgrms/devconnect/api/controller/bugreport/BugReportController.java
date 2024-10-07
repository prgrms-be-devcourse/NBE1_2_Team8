package org.prgrms.devconnect.api.controller.bugreport;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.bugreport.dto.request.BugReportRequest;
import org.prgrms.devconnect.api.controller.bugreport.dto.request.BugReportUpdateRequest;
import org.prgrms.devconnect.api.controller.bugreport.dto.response.BugReportResponse;
import org.prgrms.devconnect.api.service.bugreport.BugReportCommendService;
import org.prgrms.devconnect.api.service.bugreport.BugReportQueryService;
import org.prgrms.devconnect.domain.define.bugreport.entity.BugReport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bugreport")
@Tag(name = "버그리포트 API", description = "버그 관련 신고를 하는 게시판")
public class BugReportController {

  private final BugReportCommendService bugReportCommendService;
  private final BugReportQueryService bugReportQueryService;

  // 버그 전체 조회

  @Operation(summary = "버그 리포트 생성", description = "새로운 버그 리포트를 생성합니다.")
  @ApiResponse(responseCode = "201", description = "버그 리포트가 성공적으로 생성되었습니다.",
          content = @Content(schema = @Schema(implementation = BugReport.class)))
  @PostMapping
  public ResponseEntity<BugReport> createBugReport(@RequestBody @Valid BugReportRequest dto) {
    BugReport bugReport = bugReportCommendService.createBugReport(dto.memberId(),
            dto.url(),
            dto.content(),
            dto.bugType());

    return ResponseEntity.status(CREATED).body(bugReport);
  }

  @Operation(summary = "모든 버그 리포트 조회", description = "모든 버그 리포트를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "버그 리포트 목록을 성공적으로 반환합니다.",
          content = @Content(schema = @Schema(implementation = BugReportResponse.class)))
  @ApiResponse(responseCode = "204", description = "버그 리포트가 없습니다.")
  @GetMapping
  public ResponseEntity<List<BugReportResponse>> getAllBugReports() {
    List<BugReportResponse> results = bugReportQueryService.getAllBugReport();

    if (results.isEmpty())
      return ResponseEntity.status(NO_CONTENT).build();

    return ResponseEntity.status(OK).body(results);
  }

  @Operation(summary = "특정 버그 리포트 조회", description = "버그 리포트 ID로 버그 리포트의 상세 정보를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "버그 리포트 상세 정보를 성공적으로 반환합니다.",
          content = @Content(schema = @Schema(implementation = BugReport.class)))
  @Parameter(name = "reportId", description = "조회할 버그 리포트의 ID", required = true)
  @GetMapping("/{reportId}")
  public ResponseEntity<BugReport> getBugReportById(@PathVariable Long reportId) {
    BugReport bugReport = bugReportQueryService.getBugReport(reportId);
    return ResponseEntity.status(OK).body(bugReport);
  }

  @Operation(summary = "버그 리포트 수정", description = "특정 버그 리포트를 수정합니다.")
  @ApiResponse(responseCode = "200", description = "버그 리포트가 성공적으로 수정되었습니다.",
          content = @Content(schema = @Schema(implementation = BugReport.class)))
  @Parameter(name = "reportId", description = "수정할 버그 리포트의 ID", required = true)
  @PutMapping("/{reportId}")
  public ResponseEntity<BugReport> updateBugReport(@PathVariable Long reportId,
                                                   @RequestBody @Valid BugReportUpdateRequest dto) {
    BugReport bugReport = bugReportCommendService.updateBugReport(reportId, dto.url(), dto.content(), dto.bugType());

    return ResponseEntity.status(OK).body(bugReport);
  }

  @Operation(summary = "버그 리포트 삭제", description = "특정 버그 리포트를 삭제합니다.")
  @ApiResponse(responseCode = "204", description = "버그 리포트가 성공적으로 삭제되었습니다.")
  @Parameter(name = "reportId", description = "삭제할 버그 리포트의 ID", required = true)
  @DeleteMapping("/{reportId}")
  public ResponseEntity<Object> deleteBugReport(@PathVariable Long reportId) {
    bugReportCommendService.removeBug(reportId);

    return ResponseEntity.status(NO_CONTENT).build();
  }

  @Operation(summary = "특정 사용자의 모든 버그 리포트 조회", description = "특정 사용자의 모든 버그 리포트를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "해당 사용자의 버그 리포트 목록을 성공적으로 반환합니다.",
          content = @Content(schema = @Schema(implementation = BugReportResponse.class)))
  @ApiResponse(responseCode = "204", description = "해당 사용자의 버그 리포트가 없습니다.")
  @Parameter(name = "memberId", description = "조회할 사용자의 ID", required = true)
  @GetMapping("/member/{memberId}")
  public ResponseEntity<List<BugReportResponse>> getAllBugReportsByMemberId(@PathVariable("memberId") Long memberId) {
    List<BugReportResponse> results = bugReportQueryService.getAllBugReportByMemberId(memberId);

    if (results.isEmpty())
      return ResponseEntity.status(NO_CONTENT).build();

    return ResponseEntity.status(OK).body(results);
  }
}
