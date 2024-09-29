package org.prgrms.devconnect.api.controller.member.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.PositiveOrZero;
import org.prgrms.devconnect.domain.define.member.entity.MemberTechStackMapping;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MemberTechStackRequestDto(
    @PositiveOrZero
    Long techStackId
) {

  public MemberTechStackMapping toEntity(TechStack techStack) {
    return MemberTechStackMapping.builder()
        .techStack(techStack)
        .build();
  }

}
