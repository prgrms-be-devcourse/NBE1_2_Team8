package org.prgrms.devconnect.api.controller.board.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import org.prgrms.devconnect.domain.define.board.entity.BoardTechStackMapping;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record BoardTechStackRequestDto(
        @Schema(description = "기술 스택 ID", example = "1", required = true)
        @Positive(message = "기술 스택 ID는 0 이상이어야 합니다.")
        Long techStackId
) {
        public BoardTechStackMapping toEntity(TechStack techStack) {
                return BoardTechStackMapping.builder()
                        .techStack(techStack)
                        .build();
        }
}
