package org.prgrms.devconnect.api.controller.interest.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import org.prgrms.devconnect.api.controller.board.dto.response.BoardInfoResponseDto;
import org.prgrms.devconnect.api.controller.jobpost.dto.response.JobPostInfoResponseDto;
import org.prgrms.devconnect.domain.define.interest.entity.InterestBoard;
import org.prgrms.devconnect.domain.define.interest.entity.InterestJobPost;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "관심 게시물 전체 조회 응답 정보")
public record InterestResponseDto(

        @Schema(description = "여러 개의 단일 게시글 정보")
        List<BoardInfoResponseDto> interestBoards,

        @Schema(description = "여러 개의 채용공고 게시글 정보")
        List<JobPostInfoResponseDto> interestJobPosts
) {

  public static InterestResponseDto from(List<InterestBoard> interestBoards,
                                         List<InterestJobPost> interestJobPosts) {
    return InterestResponseDto.builder()
            .interestBoards(interestBoards.stream()
                    .map(InterestBoard::getBoard)
                    .map(BoardInfoResponseDto::from)
                    .collect(Collectors.toList()))
            .interestJobPosts(interestJobPosts.stream()
                    .map(InterestJobPost::getJobPost)
                    .map(JobPostInfoResponseDto::from)
                    .collect(Collectors.toList()))
            .build();
  }

}
