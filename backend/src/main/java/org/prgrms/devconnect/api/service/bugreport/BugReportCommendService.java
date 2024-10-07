package org.prgrms.devconnect.api.service.bugreport;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.domain.define.bugreport.entity.BugReport;
import org.prgrms.devconnect.domain.define.bugreport.entity.constant.BugType;
import org.prgrms.devconnect.domain.define.bugreport.repository.BugReportRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BugReportCommendService {

  private final BugReportRepository bugReportRepository;
  private final BugReportQueryService bugReportQueryService;
  private final MemberQueryService memberQueryService;

  // 버그리포트 생성
  public BugReport createBugReport(Long memberId, String url, String content, BugType bugType) {
    Member member = memberQueryService.getMemberByIdOrThrow(memberId);

    BugReport bugReport = BugReport.builder()
            .member(member)
            .relatedUrl(url)
            .content(content)
            .bugType(bugType)
            .build();

    bugReportRepository.save(bugReport);

    return bugReport;
  }

  // 버그리포트 삭제
  public void removeBug(Long bugreportId) {
    bugReportRepository.deleteById(bugreportId);
  }

  // 버그 리포트 수정
  public BugReport updateBugReport(Long bugReportId, String url, String content, BugType bugType) {
    BugReport bugReport = bugReportQueryService.getBugReport(bugReportId);
    bugReport.updateReport(url, content, bugType);
    bugReportRepository.save(bugReport);

    return bugReport;
  }
}
