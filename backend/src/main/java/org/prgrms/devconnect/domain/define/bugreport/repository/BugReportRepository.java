package org.prgrms.devconnect.domain.define.bugreport.repository;

import org.prgrms.devconnect.domain.define.bugreport.entity.BugReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BugReportRepository extends JpaRepository<BugReport, Long> {

  List<BugReport> findAllByMember_MemberId(Long memberId);

  // 내림차순 정렬
  List<BugReport> findAllByOrderByBugReportIdDesc();
}
