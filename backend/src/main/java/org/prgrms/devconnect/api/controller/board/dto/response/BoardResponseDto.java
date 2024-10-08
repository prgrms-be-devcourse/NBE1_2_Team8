package org.prgrms.devconnect.api.controller.board.dto.response;

import lombok.Builder;
import org.prgrms.devconnect.api.controller.techstack.dto.response.TechStackResponseDto;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.BoardTechStackMapping;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardCategory;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.board.entity.constant.ProgressWay;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record BoardResponseDto(
        Long boardId,
        Long authorId,
        String author,
        String title,
        String content,
        BoardCategory category,
        int recruitNum,
        ProgressWay progressWay,
        String progressPeriod,
        LocalDateTime endDate,
        int likes,
        int views,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,
        BoardStatus status,
        List<TechStackResponseDto>techStacks
) {
  public static BoardResponseDto from(Board board) {
    List<TechStackResponseDto> techStackDtos = board.getBoardTechStacks().stream()
            .map(BoardTechStackMapping::getTechStack)
            .map(TechStackResponseDto::from)
            .collect(Collectors.toList());

    return BoardResponseDto.builder()
            .boardId(board.getBoardId())
            .authorId(board.getMember().getMemberId())
            .author(board.getMember().getNickname())
            .title(board.getTitle())
            .content(board.getContent())
            .category(board.getCategory())
            .recruitNum(board.getRecruitNum())
            .progressWay(board.getProgressWay())
            .progressPeriod(board.getProgressPeriod())
            .endDate(board.getEndDate())
            .likes(board.getLikes())
            .views(board.getViews())
            .createdDate(board.getCreatedAt())
            .updatedDate(board.getUpdatedAt())
            .status(board.getStatus())
            .techStacks(techStackDtos)
            .build();
  }
}
