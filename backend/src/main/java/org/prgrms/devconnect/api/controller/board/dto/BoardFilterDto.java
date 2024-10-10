package org.prgrms.devconnect.api.controller.board.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardCategory;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.board.entity.constant.ProgressWay;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record BoardFilterDto(
         BoardCategory category,
         BoardStatus status,
         List<Long>techStackIds,
         ProgressWay progressWay
){
  public boolean isEmpty() {
    return category == null && status == null &&
            (techStackIds == null || techStackIds.isEmpty()) &&
            progressWay == null;
  }
}
