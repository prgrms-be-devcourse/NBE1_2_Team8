package org.prgrms.devconnect.api.service.board;

import org.prgrms.devconnect.api.controller.board.BoardCreateDTO;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.board.BoardException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.repository.BoardRepository;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.jobpost.repository.JobPostRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.domain.define.techstack.repository.TechStackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BoardService {
  @Autowired
  private BoardRepository boardRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private JobPostRepository jobPostRepsitory;
  @Autowired
  private TechStackRepository techStackRepository;

  @Transactional
  public Long createBoard(BoardCreateDTO boardCreateDTO) {
    Member member = memberRepository.findById(boardCreateDTO.getMemberId())
            .orElseThrow(() -> new BoardException(ExceptionCode.NOT_FOUNT_MEMBER));

    // JobPost 존재 여부 확인. 없을 경우 null로 처리
    JobPost jobPost = null;
    if (boardCreateDTO.getJobPostId() != null) {
      jobPost = jobPostRepsitory.findById(boardCreateDTO.getJobPostId())
              .orElse(null); // Optional 처리
    }

    // 조회된 기술 스택의 개수가 사용자가 선택한 ID 리스트와 일치하지 않으면 예외 발생
    List<TechStack> techStacks = techStackRepository.findAllById(boardCreateDTO.getTechStackIds());
    if (techStacks.size() != boardCreateDTO.getTechStackIds().size()) {
      throw new BoardException(ExceptionCode.NOT_FOUND_BOARD_TECH_STACK);
    }

    Board board=boardCreateDTO.toEntity(member,jobPost,techStacks);
    Board savedBoard=boardRepository.save(board);

    return savedBoard.getBoardId();
  }
}
