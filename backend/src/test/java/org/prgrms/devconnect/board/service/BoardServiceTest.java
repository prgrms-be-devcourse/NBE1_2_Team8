package org.prgrms.devconnect.board.service;

import org.junit.jupiter.api.Test;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardCreateRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardTechStackRequestDto;
import org.prgrms.devconnect.api.service.board.BoardService;
import org.prgrms.devconnect.board.MemberFixture;
import org.prgrms.devconnect.board.TechStackFixture;
import org.prgrms.devconnect.common.exception.board.BoardException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.repository.BoardRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.domain.define.techstack.repository.TechStackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class BoardServiceTest {

  @Autowired
  private BoardService boardService;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private BoardRepository boardRepository;

  @Autowired
  private TechStackRepository techStackRepository;

  @Test
  public void 테스트시_필요한_객체_미리생성() {

    // given
    Member member = MemberFixture.createMember();
    Member savedMember = memberRepository.saveAndFlush(member);
    TechStack techStack = TechStackFixture.createTechStack();
    TechStack savedTechStack = techStackRepository.saveAndFlush(techStack);

    // then
    assertNotNull(savedMember.getMemberId()); // ID가 정상적으로 생성되었는지 확인
    assertNotNull(savedTechStack.getTechStackId()); // ID가 정상적으로 생성되었는지 확인
  }

  @Test
  public void 게시글생성() {
    // given
    Long existingMemberId = 1L;
    Long existingTechStackId = 1L;

    BoardCreateRequestDto boardCreateRequestDto = new BoardCreateRequestDto(
            existingMemberId, null,
            "Spring 스터디 모집합니다.",
            "SpringBoot, JPA, JWT, Security까지 패키지로 공부할 사람 구해요",
            "개발", 5, "온라인", "5개월", LocalDateTime.now(),
            List.of(new BoardTechStackRequestDto(existingTechStackId))
    );

    // when
    Long result = boardService.createBoard(boardCreateRequestDto);

    // then
    Board savedBoard = boardRepository.findById(result).orElse(null);
    assertNotNull(savedBoard); // 저장된 Board가 null이 아님을 확인
    assertEquals("Spring 스터디 모집합니다.", savedBoard.getTitle()); // 제목이 일치하는지 확인
    assertEquals(existingMemberId, savedBoard.getMember().getMemberId()); // Member가 일치하는지 확인
  }


  @Test
  public void 멤버ID가_유효하지_않은_경우() {
    // given
    Long invalidMemberId = 999L;
    Long existingTechStackId = 1L;

    BoardCreateRequestDto boardCreateRequestDto = new BoardCreateRequestDto(
            invalidMemberId, null,
            "Invalid Member Test",
            "유효하지 않은 멤버로 게시판 생성 테스트",
            "개발", 5, "온라인", "5개월", LocalDateTime.now(),
            List.of(new BoardTechStackRequestDto(existingTechStackId))
    );

    // when & then
    assertThrows(BoardException.class, () -> {
      boardService.createBoard(boardCreateRequestDto);
    });
  }

  @Test
  public void 기술스택ID가_유효하지_않은_경우() {
    // given
    Long existingMemberId = 1L;
    Long invalidTechStackId = 999L;

    BoardCreateRequestDto boardCreateRequestDto = new BoardCreateRequestDto(
            existingMemberId, null,
            "Invalid Tech Stack Test",
            "유효하지 않은 기술 스택으로 게시판 생성 테스트",
            "개발", 5, "온라인", "5개월", LocalDateTime.now(),
            List.of(new BoardTechStackRequestDto(invalidTechStackId))
    );

    // when & then
    assertThrows(BoardException.class, () -> {
      boardService.createBoard(boardCreateRequestDto);
    });

  }
}
