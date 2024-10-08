package org.prgrms.devconnect.api.service.bugreport;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.bugreport.dto.response.BugReportResponse;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.domain.define.bugreport.entity.BugReport;
import org.prgrms.devconnect.domain.define.bugreport.entity.constant.BugType;
import org.prgrms.devconnect.domain.define.bugreport.repository.BugReportRepository;
import org.prgrms.devconnect.domain.define.fixture.BugReportFixture;
import org.prgrms.devconnect.domain.define.fixture.MemberFixture;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BugReportCommendServiceTest {

  @InjectMocks
  private BugReportCommendService bugReportCommendService;

  @Mock
  private BugReportRepository bugReportRepository;

  @Mock
  private BugReportQueryService bugReportQueryService;

  @Mock
  private MemberQueryService memberQueryService;

  @Test
  @DisplayName("버그리포트 생성 테스트")
  public void 버그리포트_생성_테스트() {
    // Given
    Long memberId = 1L;
    Member member = MemberFixture.createMember(TechStack.builder().build());
    String url = "http://example.com";
    String content = "Bug description";
    BugType bugType = BugType.NOT_FOUND;
    BugReport bugReport = BugReportFixture.createBugReport();

    when(memberQueryService.getMemberByIdOrThrow(memberId)).thenReturn(member);
    when(bugReportRepository.save(any(BugReport.class))).thenReturn(bugReport);

    // When
    BugReportResponse result = bugReportCommendService.createBugReport(memberId, url, content, bugType);

    // Then
    assertNotNull(result);
    assertEquals(url, result.relatedUrl());
    verify(bugReportRepository, times(1)).save(any(BugReport.class));
  }

  @Test
  @DisplayName("버그리포트 삭제 테스트")
  public void 버그리포트_삭제_테스트() {
    // Given
    Long bugReportId = 1L;

    // When
    bugReportCommendService.removeBug(bugReportId);

    // Then
    verify(bugReportRepository, times(1)).deleteById(bugReportId);
  }

  @Test
  @DisplayName("버그리포트 수정 테스트")
  public void testUpdateBugReport() {
    // Given
    Long bugReportId = 1L;
    String url = "http://example.com";
    String content = "Updated bug description";
    BugType bugType = BugType.OTHER;
    BugReport bugReport = BugReportFixture.createBugReport();

    // Mock behavior
    when(bugReportQueryService.getBugReport(bugReportId)).thenReturn(bugReport);

    // When
    bugReportCommendService.updateBugReport(bugReportId, url, content, bugType);

    // Then
    verify(bugReportQueryService, times(1)).getBugReport(bugReportId);
    verify(bugReportRepository, times(1)).save(bugReport);
    assertEquals(url, bugReport.getRelatedUrl());
    assertEquals(content, bugReport.getContent());
    assertEquals(bugType, bugReport.getBugType());
  }
}
