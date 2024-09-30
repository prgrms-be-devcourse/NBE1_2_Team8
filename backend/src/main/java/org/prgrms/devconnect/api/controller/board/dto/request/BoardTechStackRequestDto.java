package org.prgrms.devconnect.api.controller.board.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Positive;
import org.prgrms.devconnect.domain.define.board.entity.BoardTechStackMapping;
import org.prgrms.devconnect.domain.define.member.entity.MemberTechStackMapping;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record BoardTechStackRequestDto(
        @Positive(message = "기술 스택 ID는 0 이상이어야 합니다.")
        Long techStackId
) {
        public BoardTechStackMapping toEntity(TechStack techStack) {
                return BoardTechStackMapping.builder()
                        .techStack(techStack)
                        .build();
        }
}
