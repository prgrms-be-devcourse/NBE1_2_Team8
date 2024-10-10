package org.prgrms.devconnect.api.service.bugreport;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.bugreport.dto.response.BugReportResponse;
import org.prgrms.devconnect.domain.define.bugreport.entity.BugReport;
import org.prgrms.devconnect.domain.define.bugreport.repository.BugReportRepository;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BugReportQueryServiceTest {
  @InjectMocks
  private BugReportQueryService bugReportQueryService;

  @Mock
  private BugReportRepository bugReportRepository;

  @Test
  @DisplayName("버그리포트 조회 테스트")
  public void 버그리포트_조회_테스트() {
    // Given
    Long bugReportId = 1L;
    BugReport bugReport = BugReport.builder().build();

    when(bugReportRepository.findById(bugReportId)).thenReturn(Optional.of(bugReport));

    // When
    BugReport result = bugReportQueryService.getBugReport(bugReportId);

    // Then
    assertNotNull(result);
    verify(bugReportRepository, times(1)).findById(bugReportId);
  }

  @Test
  @DisplayName("버그리포트 전체 조회 테스트")
  public void 버그리포트_전체_조회_테스트() {
    // Given
    List<BugReport> bugReports = new ArrayList<>();
    bugReports.add(BugReport.builder().build());
    bugReports.add(BugReport.builder().build());
    bugReports.add(BugReport.builder().build());

    when(bugReportRepository.findAll()).thenReturn(bugReports);

    // When
    List<BugReportResponse> result = bugReportQueryService.getAllBugReport();

    // Then
    assertEquals(3, result.size());
    verify(bugReportRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("사용자 버그리포트 전체 조회")
  public void 사용자_버그리포트_전체_조회() {
    // Given
    Long memberId = 1L;
    List<BugReport> bugReports = new ArrayList<>();
    bugReports.add(BugReport.builder().build());

    when(bugReportRepository.findAllByMember_MemberId(memberId)).thenReturn(bugReports);

    // When
    List<BugReportResponse> result = bugReportQueryService.getAllBugReportByMemberId(memberId);

    // Then
    assertEquals(1, result.size());
    verify(bugReportRepository, times(1)).findAllByMember_MemberId(memberId);
  }
}
