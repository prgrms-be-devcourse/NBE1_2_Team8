package org.prgrms.devconnect.api.service.interest;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.board.dto.response.BoardInfoResponseDto;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.interest.InterestException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.interest.entity.InterestBoard;
import org.prgrms.devconnect.domain.define.interest.repository.InterestBoardRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterestQueryService {

  private final InterestBoardRepository interestBoardRepository;
  private final MemberQueryService memberQueryService;

  public List<BoardInfoResponseDto> getInterestBoardsByMemberId(Long memberId) {
    Member member = memberQueryService.getMemberByIdOrThrow(memberId);
    List<InterestBoard> interestBoards = interestBoardRepository.findAllByMemberWithBoard(
        member);

    return interestBoards.stream().map(InterestBoard::getBoard)
        .map(BoardInfoResponseDto::from)
        .collect(Collectors.toList());
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
}
