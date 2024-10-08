package org.prgrms.devconnect.domain.define.fixture;

import org.prgrms.devconnect.domain.define.bugreport.entity.BugReport;
import org.prgrms.devconnect.domain.define.bugreport.entity.constant.BugType;

public class BugReportFixture {
  public static BugReport createBugReport(){
    return BugReport.builder()
            .relatedUrl("http://example.com")
            .content("Bug description")
            .bugType(BugType.NOT_FOUND)
            .build();
  }
}
