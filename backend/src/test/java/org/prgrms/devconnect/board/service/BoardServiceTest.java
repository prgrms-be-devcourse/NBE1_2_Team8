package org.prgrms.devconnect.board.service;

import org.junit.jupiter.api.Test;
import org.prgrms.devconnect.api.controller.board.BoardCreateDTO;
import org.prgrms.devconnect.api.service.board.BoardService;
import org.prgrms.devconnect.common.exception.board.BoardException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.repository.BoardRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.entity.constant.Interest;
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
    // 1. Member 생성 및 저장
    Member member = Member.builder()
            .email("testUser@example.com")
            .password("password123")
            .nickname("testUser")
            .affiliation("Test Company")
            .career(5) // 경력 5년
            .selfIntroduction("새로운 tech에 관심많은 열정적인 개발자!")
            .blogLink("https://testuserblog.com")
            .githubLink("https://github.com/testUser")
            .interest(Interest.JOBPOST) // 예시로 DEVELOPMENT 사용
            .build();

    Member savedMember = memberRepository.saveAndFlush(member);
    assertNotNull(savedMember.getMemberId()); // ID가 정상적으로 생성되었는지 확인

    // 2. TechStack 생성 및 저장
    TechStack techStack = TechStack.builder()
            .name("Spring")
            .code("1234")
            .build();
    TechStack savedTechStack = techStackRepository.saveAndFlush(techStack);
    assertNotNull(savedTechStack.getId()); // ID가 정상적으로 생성되었는지 확인
  }

  @Test
  public void 게시글생성() {
    Long existingMemberId = 1L; // 미리 저장된 멤버 ID
    Long existingTechStackId = 1L; // 미리 저장된 기술 스택 ID
    // BoardCreateDTO 설정
    BoardCreateDTO boardCreateDTO = BoardCreateDTO.builder()
            .memberId(existingMemberId)
            .jobPostId(null) // JobPost는 null로 설정
            .title("Spring 스터디 모집합니다.")
            .content("SpringBoot, JPA, JWT, Security까지 패키지로 공부할 사람 구해요")
            .category("개발")
            .recruitNum(5)
            .progressWay("온라인")
            .progressPeriod("5개월")
            .endDate(LocalDateTime.now())
            .techStackIds(List.of(existingTechStackId))
            .build();

    // 실행: 실제 DB에 저장
    Long result = boardService.createBoard(boardCreateDTO);

    // DB에서 실제로 저장된 Board를 조회하여 검증
    Board savedBoard = boardRepository.findById(result).orElse(null);

    assertNotNull(savedBoard); // 저장된 Board가 null이 아님을 확인
    assertEquals("Spring 스터디 모집합니다.", savedBoard.getTitle()); // 제목이 일치하는지 확인
    assertEquals(existingMemberId, savedBoard.getMember().getMemberId()); // Member가 일치하는지 확인
  }
  @Test
  public void 멤버ID가_유효하지_않은_경우() {
    Long invalidMemberId = 999L; // 유효하지 않은 멤버 ID
    Long existingTechStackId = 1L; // 미리 저장된 기술 스택 ID

    BoardCreateDTO boardCreateDTO = BoardCreateDTO.builder()
            .memberId(invalidMemberId)
            .jobPostId(null)
            .title("Invalid Member Test")
            .content("유효하지 않은 멤버로 게시판 생성 테스트")
            .category("개발")
            .recruitNum(5)
            .progressWay("온라인")
            .progressPeriod("5개월")
            .endDate(LocalDateTime.now())
            .techStackIds(List.of(existingTechStackId))
            .build();

    assertThrows(BoardException.class, () -> {
      boardService.createBoard(boardCreateDTO);
    });
  }

  @Test
  public void 기술스택ID가_유효하지_않은_경우 () {
    Long existingMemberId = 1L;
    Long invalidTechStackId = 999L; // 유효하지 않은 기술 스택 ID

    BoardCreateDTO boardCreateDTO = BoardCreateDTO.builder()
            .memberId(existingMemberId)
            .jobPostId(null)
            .title("Invalid Tech Stack Test")
            .content("유효하지 않은 기술 스택으로 게시판 생성 테스트")
            .category("개발")
            .recruitNum(5)
            .progressWay("온라인")
            .progressPeriod("5개월")
            .endDate(LocalDateTime.now())
            .techStackIds(List.of(invalidTechStackId))
            .build();

    assertThrows(BoardException.class, () -> {
      boardService.createBoard(boardCreateDTO);
    });
  }

}
