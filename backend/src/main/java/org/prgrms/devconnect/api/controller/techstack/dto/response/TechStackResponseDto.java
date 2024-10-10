package org.prgrms.devconnect.api.controller.techstack.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TechStackResponseDto(

        @Schema(description = "기술 스택 ID", example = "1")
        Long techStackId,

        @Schema(description = "기술 스택 이름", example = "Java")
        String name,

        @Schema(description = "기술 스택 코드", example = "200")
        String code
) {

  public static TechStackResponseDto from(TechStack techStack) {
    return TechStackResponseDto.builder()
            .techStackId(techStack.getTechStackId())
            .name(techStack.getName())
            .code(techStack.getCode())
            .build();
  }
}