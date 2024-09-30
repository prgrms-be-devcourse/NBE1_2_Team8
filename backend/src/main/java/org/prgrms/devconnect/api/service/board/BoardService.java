package org.prgrms.devconnect.api.service.board;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardCreateRequestDto;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.board.BoardException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.BoardTechStackMapping;
import org.prgrms.devconnect.domain.define.board.repository.BoardRepository;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.jobpost.entity.repository.JobPostRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.domain.define.techstack.repository.TechStackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;

  //TODO 수정 필요
  private final MemberRepository memberRepository;
//  private final MemberService memberService;

  private final JobPostRepository jobPostRepository;

  private final TechStackRepository techStackRepository;


  @Transactional
  public Long createBoard(BoardCreateRequestDto boardCreateRequestDto) {
    //TODO 수정 필요
//    Member member = memberService.findMemberById(boardCreateRequestDto.memberId());
    Member member = memberRepository.findById(boardCreateRequestDto.memberId()).orElseThrow(() -> new RuntimeException("Member Not Found"));

    // JobPost 존재 여부 확인. 없을 경우 null로 처리
    JobPost jobPost = null;
    if (boardCreateRequestDto.jobPostId() != null) {
      jobPost = jobPostRepository.findById(boardCreateRequestDto.jobPostId())
              .orElse(null); // Optional 처리
    }

    List<BoardTechStackMapping> boardTechStackMappings = boardCreateRequestDto.techStackRequests().stream()
            .map(requestdto -> {
              TechStack techStack = techStackRepository.findById(requestdto.techStackId())
                      .orElseThrow(() -> new BoardException(ExceptionCode.NOT_FOUND_BOARD_TECH_STACK));
              return requestdto.toEntity(techStack);
            })
            .collect(Collectors.toList());

    Board board = boardCreateRequestDto.toEntity(member, jobPost, boardTechStackMappings);
    boardRepository.save(board);

    return board.getBoardId();
  }
}
