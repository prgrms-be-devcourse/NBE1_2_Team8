package org.prgrms.devconnect.api.controller.board.dto.request;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardCategory;
import org.prgrms.devconnect.domain.define.board.entity.constant.ProgressWay;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record BoardUpdateRequestDto(
        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        String content,

        @NotNull(message = "카테고리는 필수입니다.")
        BoardCategory category,

        @NotNull(message = "모집 인원은 필수입니다.")
        int recruitNum,

        @NotNull(message = "진행 방식은 필수입니다.")
        ProgressWay progressWay,

        @NotBlank(message = "진행 기간은 필수입니다.")
        String progressPeriod,

        @NotNull(message = "종료 날짜는 필수입니다.")
        LocalDateTime endDate
) {}
