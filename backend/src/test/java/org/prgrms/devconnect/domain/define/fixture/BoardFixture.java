package org.prgrms.devconnect.domain.define.fixture;

import java.time.LocalDateTime;
import java.util.List;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardCreateRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardTechStackRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardUpdateRequestDto;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardCategory;
import org.prgrms.devconnect.domain.define.board.entity.constant.ProgressWay;
import org.prgrms.devconnect.domain.define.member.entity.Member;

public class BoardFixture {

  public static Board createBoard(Member member) {
    return Board.builder()
        .member(member)
        .jobPost(null)
        .title("Spring 스터디 모집합니다.")
        .content("SpringBoot, JPA, JWT, Security까지 패키지로 공부할 사람 구해요")
        .category(BoardCategory.STUDY)
        .recruitNum(5)
        .progressWay(ProgressWay.ONLINE)
        .progressPeriod("3개월")
        .endDate(LocalDateTime.now())
        .boardTechStacks(List.of())
        .build();
  }

  // BoardCreateRequestDto
  public static BoardCreateRequestDto createBoardCreateRequestDto(Long memberId, String title) {
    return BoardCreateRequestDto.builder()
        .memberId(memberId)
        .jobPostId(null)
        .title(title)
        .content("SpringBoot, JPA, JWT, Security까지 패키지로 공부할 사람 구해요")
        .category(BoardCategory.STUDY)
        .recruitNum(5)
        .progressWay(ProgressWay.ONLINE)
        .progressPeriod("5개월")
        .endDate(LocalDateTime.now())
        .techStackRequests(List.of())
        .build();
  }

  public static BoardCreateRequestDto createBoardCreateRequestDto(Long memberId, String title,
      List<BoardTechStackRequestDto> techStackRequestDtos) {
    return BoardCreateRequestDto.builder()
        .memberId(memberId)
        .jobPostId(null)
        .title(title)
        .content("SpringBoot, JPA, JWT, Security까지 패키지로 공부할 사람 구해요")
        .category(BoardCategory.STUDY)
        .recruitNum(5)
        .progressWay(ProgressWay.ONLINE)
        .progressPeriod("5개월")
        .endDate(LocalDateTime.now())
        .techStackRequests(techStackRequestDtos)
        .build();
  }

  //BoardUpdateRequestDto
  public static BoardUpdateRequestDto createBoardUpdateRequestDto(String title, String content) {
    return BoardUpdateRequestDto.builder()
        .title(title)
        .content(content)
        .category(BoardCategory.STUDY)
        .recruitNum(5)
        .progressWay(ProgressWay.ONLINE)
        .progressPeriod("5개월")
        .endDate(LocalDateTime.now())
        .build();
  }
}
