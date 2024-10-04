package org.prgrms.devconnect.api.service.bugreport;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.domain.define.bugreport.entity.constant.BugType;
import org.prgrms.devconnect.domain.define.bugreport.repository.BugReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BugReportCommendService {

  private final BugReportRepository bugReportRepository;

  public void removeBug(Long bugreportId){
    bugReportRepository.deleteById(bugreportId);
  }

  public void updateBugReport(Long memberId, String content, BugType bugType){

  }
}
