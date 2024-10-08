package org.prgrms.devconnect.api.controller.bugreport.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import org.prgrms.devconnect.domain.define.bugreport.entity.constant.BugType;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "버그리포트 생성 정보")
public record BugReportRequest(

        @NotNull(message = "사용자 ID는 필수입니다")
        @Schema(description = "사용자 ID", example = "2", required = true)
        Long memberId,

        @NotEmpty(message = "URL은 필수입니다")
        @URL(message = "올바른 URL 형식을 입력하세요")
        @Schema(description = "관련 URL", example = "http://example.com", required = true)
        String url,

        @NotEmpty(message = "설명을 다시 적어주세요")
        @Schema(description = "버그 내용", example = "어떤 기능에서 문제가 발생했습니다.", required = true)
        String content,

        @NotNull(message = "버그 타입은 필수입니다")
        @Schema(description = "버그 타입", example = "EXPIRED", required = true)
        BugType bugType
) {
}
