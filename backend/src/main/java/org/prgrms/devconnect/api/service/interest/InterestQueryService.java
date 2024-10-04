package org.prgrms.devconnect.api.service.interest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.interest.dto.response.InterestResponseDto;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.interest.InterestException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.interest.entity.InterestBoard;
import org.prgrms.devconnect.domain.define.interest.entity.InterestJobPost;
import org.prgrms.devconnect.domain.define.interest.repository.InterestBoardRepository;
import org.prgrms.devconnect.domain.define.interest.repository.InterestJobPostRepository;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterestQueryService {

  private final InterestBoardRepository interestBoardRepository;
  private final InterestJobPostRepository interestJobPostRepository;
  private final MemberQueryService memberQueryService;

  public InterestResponseDto getInterestsByMemberId(Long memberId) {
    Member member = memberQueryService.getMemberByIdOrThrow(memberId);
    List<InterestBoard> interestBoards = interestBoardRepository.findAllByMemberWithBoard(
        member);

    List<InterestJobPost> interestJobPosts = interestJobPostRepository.findAllByMemberWithJobPost(
        member);

    return InterestResponseDto.from(interestBoards, interestJobPosts);
  }

  public InterestBoard getInterestBoardByMemberIdAndBoardIdOrThrow(Long memberId, Long boardId) {
    return interestBoardRepository.findByMemberIdAndBoardId(memberId, boardId).orElseThrow(
        () -> new InterestException(ExceptionCode.NOT_FOUND_INTEREST_BOARD)
    );
  }

  public void validateDuplicatedInterestBoard(Member member, Board board) {
    if (interestBoardRepository.existsByMemberAndBoard(member, board)) {
      throw new InterestException(ExceptionCode.DUPLICATED_INTEREST_BOARD);
    }
  }

  public void validateDuplicatedInterestJobPost(Member member, JobPost jobPost) {
    if (interestJobPostRepository.existsByMemberAndJobPost(member, jobPost)) {
      throw new InterestException(ExceptionCode.DUPLICATED_INTEREST_JOB_POST);
    }
  }
}
