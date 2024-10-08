package org.prgrms.devconnect.api.service.bugreport;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.bugreport.dto.response.BugReportResponse;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.bugreport.BugReportException;
import org.prgrms.devconnect.domain.define.bugreport.entity.BugReport;
import org.prgrms.devconnect.domain.define.bugreport.repository.BugReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BugReportQueryService {

  private final BugReportRepository bugReportRepository;

  // 버그리포트 상세 조회
  public BugReport getBugReport(Long bugreportId){
    return bugReportRepository.findById(bugreportId)
            .orElseThrow(()-> new BugReportException(ExceptionCode.NOT_FOUND_BUG_REPORT));
  }

  // 모든 버그리포트 확인
  public List<BugReportResponse> getAllBugReport(){
    return bugReportRepository.findAllByOrderByBugReportIdDesc()
            .stream()
            .map(BugReportResponse::from)
            .collect(Collectors.toList());
  }


  // 사용자가 생성한 모든 버그리포트 확인
  public List<BugReportResponse> getAllBugReportByMemberId(Long memberId){
    return bugReportRepository.findAllByMember_MemberId(memberId)
            .stream()
            .map(BugReportResponse::from)
            .collect(Collectors.toList());
  }
}
