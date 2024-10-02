package org.prgrms.devconnect.api.service.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.prgrms.devconnect.domain.define.fixture.BoardFixture.createBoardCreateRequestDto;
import static org.prgrms.devconnect.domain.define.fixture.BoardFixture.createBoardUpdateRequestDto;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardCreateRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardTechStackRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardUpdateRequestDto;
import org.prgrms.devconnect.common.exception.member.MemberException;
import org.prgrms.devconnect.common.exception.techstack.TechStackException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardCategory;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.board.entity.constant.ProgressWay;
import org.prgrms.devconnect.domain.define.board.repository.BoardRepository;
import org.prgrms.devconnect.domain.define.fixture.BoardFixture;
import org.prgrms.devconnect.domain.define.fixture.MemberFixture;
import org.prgrms.devconnect.domain.define.fixture.TechStackFixture;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.domain.define.techstack.repository.TechStackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

  private Board savedBoard;

  @BeforeEach
  public void setUp() {
    // given
    TechStack techStack = TechStackFixture.createTechStack();
    savedTechStack = techStackRepository.saveAndFlush(techStack);
    Member member = MemberFixture.createMember(savedTechStack);
    savedMember = memberRepository.saveAndFlush(member);
    Board board = BoardFixture.createBoard(savedMember);
    savedBoard = boardRepository.saveAndFlush(board);
  }


  @Test
  public void 게시글생성() {
    // given
    Long existingMemberId = savedMember.getMemberId();
    String title = "Spring 스터디 모집합니다.";

    BoardCreateRequestDto boardCreateRequestDto = createBoardCreateRequestDto(
        existingMemberId, title);

    // when
    Long result = boardCommandService.createBoard(boardCreateRequestDto);

    // then
    Board savedBoard = boardRepository.findById(result).orElse(null);
    assertNotNull(savedBoard); // 저장된 Board가 null이 아님을 확인
    assertEquals(title, savedBoard.getTitle()); // 제목이 일치하는지 확인
    assertEquals(existingMemberId, savedBoard.getMember().getMemberId()); // Member가 일치하는지 확인
  }


  @Test
  public void 멤버ID가_유효하지_않은_경우() {
    // given
    Long invalidMemberId = 999L;

    BoardCreateRequestDto boardCreateRequestDto = createBoardCreateRequestDto(invalidMemberId,
        "Invalid Member Test");

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

    List<BoardTechStackRequestDto> techStackRequestDtos = List.of(
        new BoardTechStackRequestDto(invalidTechStackId));

    BoardCreateRequestDto boardCreateRequestDto = createBoardCreateRequestDto(existingMemberId,
        "Invalid Tech Stack Test", techStackRequestDtos);


    // when & then
    assertThrows(TechStackException.class, () -> {
      boardCommandService.createBoard(boardCreateRequestDto);
    });

  }

  @Test
  public void 게시판수정_기술스택_제외하고() {
    // given
    Long existBoardId = savedBoard.getBoardId();
    String updateTitle = "JavaScript 스터디 모집합니다.";
    String updateContent = "JavaScript 공부할 사람 구해요";

    BoardUpdateRequestDto boardUpdateRequestDto = createBoardUpdateRequestDto(updateTitle,
        updateContent);

    // when
    boardCommandService.updateBoard(existBoardId, boardUpdateRequestDto);

    // then
    Board savedBoard = boardRepository.findById(existBoardId).orElse(null);
    assertNotNull(savedBoard);
    assertEquals(updateTitle, savedBoard.getTitle());
    assertEquals(updateContent, savedBoard.getContent());
  }

  @Test
  public void 게시판삭제() {
    // given
    Long boardId = savedBoard.getBoardId();

    // when
    boardCommandService.deleteBoard(boardId);

    // then
    Board deletedBoard = boardRepository.findById(boardId).orElse(null);
    assertNotNull(deletedBoard);
    assertEquals(BoardStatus.DELETED, deletedBoard.getStatus());

  }

}
