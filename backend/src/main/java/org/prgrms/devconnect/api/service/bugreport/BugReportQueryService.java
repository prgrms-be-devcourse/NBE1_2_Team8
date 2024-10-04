package org.prgrms.devconnect.api.service.bugreport;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.bugreport.BugReportException;
import org.prgrms.devconnect.domain.define.bugreport.entity.BugReport;
import org.prgrms.devconnect.domain.define.bugreport.repository.BugReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BugReportQueryService {

  private final BugReportRepository bugReportRepository;

  public BugReport getBugReport(Long bugreportId){
    return bugReportRepository.findById(bugreportId)
            .orElseThrow(()-> new BugReportException(ExceptionCode.NOT_FOUND_BUG_REPORT));
  }

  public List<BugReport> getAllBugReport(){
    return bugReportRepository.findAll();
  }

  public List<BugReport> getAllBugReportByMemberId(Long memberId){
    List<BugReport> results = bugReportRepository.findAllByMember_MemberId(memberId);
    return results;
  }
}
