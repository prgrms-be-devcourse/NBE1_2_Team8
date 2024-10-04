package org.prgrms.devconnect.domain.define.bugreport.repository;

import org.prgrms.devconnect.domain.define.bugreport.entity.BugReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugReportRepository extends JpaRepository<BugReport, Long> {
}
