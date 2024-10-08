package org.prgrms.devconnect.api.controller.bugreport.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.bugreport.entity.BugReport;
import org.prgrms.devconnect.domain.define.bugreport.entity.constant.BugType;

import java.time.LocalDateTime;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "버그 리포트 응답 정보")
public record BugReportResponse(
        @Schema(description = "버그 리포트 ID", example = "123")
        Long bugReportId,

        @Schema(description = "사용자 ID", example = "456")
        Long memberId,

        @Schema(description = "관련 URL", example = "http://example.com")
        String relatedUrl,

        @Schema(description = "버그 타입", example = "CRITICAL")
        BugType bugType,

        @Schema(description = "생성 시간", example = "2024-10-04T10:15:30")
        LocalDateTime createdAt,

        @Schema(description = "수정 시간", example = "2024-10-04T10:15:30")
        LocalDateTime updatedAt
) {
  public static BugReportResponse from(BugReport bugReport) {
    return BugReportResponse.builder()
            .bugReportId(bugReport.getBugReportId())
            .memberId(bugReport.getMember().getMemberId())
            .relatedUrl(bugReport.getRelatedUrl())
            .bugType(bugReport.getBugType())
            .createdAt(bugReport.getCreatedAt())
            .updatedAt(bugReport.getUpdatedAt())
            .build();
  }
}
