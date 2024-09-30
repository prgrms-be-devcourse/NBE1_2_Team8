package org.prgrms.devconnect.api.service.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardCreateRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardTechStackRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardUpdateRequestDto;
import org.prgrms.devconnect.common.exception.member.MemberException;
import org.prgrms.devconnect.common.exception.techstack.TechStackException;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.fixture.MemberFixture;
import org.prgrms.devconnect.domain.define.fixture.TechStackFixture;
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
class BoardCommandServiceTest {

  @Autowired
  private BoardCommandService boardCommandService;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private BoardRepository boardRepository;

  @Autowired
  private TechStackRepository techStackRepository;

  private Member savedMember;
  private TechStack savedTechStack;

  @BeforeEach
  public void setUp() {
    // given
    TechStack techStack = TechStackFixture.createTechStack();
    savedTechStack = techStackRepository.saveAndFlush(techStack);
    Member member = MemberFixture.createMember(savedTechStack);
    savedMember = memberRepository.saveAndFlush(member);
  }


  @Test
  public void 게시글생성() {
    // given
    Long existingMemberId = savedMember.getMemberId();
    Long existingTechStackId = savedTechStack.getTechStackId();

    BoardCreateRequestDto boardCreateRequestDto = new BoardCreateRequestDto(
            existingMemberId, null,
            "Spring 스터디 모집합니다.",
            "SpringBoot, JPA, JWT, Security까지 패키지로 공부할 사람 구해요",
            "개발", 5, "온라인", "5개월", LocalDateTime.now(),
            List.of(new BoardTechStackRequestDto(existingTechStackId))
    );

    // when
    Long result = boardCommandService.createBoard(boardCreateRequestDto);

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
    Long existingTechStackId = savedTechStack.getTechStackId();

    BoardCreateRequestDto boardCreateRequestDto = new BoardCreateRequestDto(
            invalidMemberId, null,
            "Invalid Member Test",
            "유효하지 않은 멤버로 게시판 생성 테스트",
            "개발", 5, "온라인", "5개월", LocalDateTime.now(),
            List.of(new BoardTechStackRequestDto(existingTechStackId))
    );

    // when & then
    assertThrows(MemberException.class, () -> {
      boardCommandService.createBoard(boardCreateRequestDto);
    });
  }

  @Test
  public void 기술스택ID가_유효하지_않은_경우() {
    // given
    Long existingMemberId = savedMember.getMemberId();
    Long invalidTechStackId = 999L;

    BoardCreateRequestDto boardCreateRequestDto = new BoardCreateRequestDto(
            existingMemberId, null,
            "Invalid Tech Stack Test",
            "유효하지 않은 기술 스택으로 게시판 생성 테스트",
            "개발", 5, "온라인", "5개월", LocalDateTime.now(),
            List.of(new BoardTechStackRequestDto(invalidTechStackId))
    );

    // when & then
    assertThrows(TechStackException.class, () -> {
      boardCommandService.createBoard(boardCreateRequestDto);
    });

  }

  @Test
  public void 게시판수정_기술스택_제외하고(){
    // given
    BoardUpdateRequestDto boardUpdateRequestDto = new BoardUpdateRequestDto(
            "JavaScript 스터디 모집합니다.",
            "JavaScript 공부할 사람 구해요",
            "개발",
            5,
            "온라인",
            "5개월",
            LocalDateTime.now()
    );

    // when
    boardCommandService.updateBoard(1L,boardUpdateRequestDto);

    // then
    Board savedBoard = boardRepository.findById(1L).orElse(null);
    assertNotNull(savedBoard);
    assertEquals("JavaScript 스터디 모집합니다.", savedBoard.getTitle());
    assertEquals("JavaScript 공부할 사람 구해요", savedBoard.getContent());
    assertEquals(5, savedBoard.getRecruitNum());
    assertEquals("온라인", savedBoard.getProgressWay());
    assertEquals("5개월", savedBoard.getProgressPeriod());

  }

  @Test
  public void 게시판삭제(){
    // given
    Long boardId=2L;

    // when
    boardCommandService.deleteBoard(boardId);

    // then
    Board deletedBoard = boardRepository.findById(boardId).orElse(null);
    assertNotNull(deletedBoard);
    assertEquals(BoardStatus.DELETED, deletedBoard.getStatus());

  }

}
