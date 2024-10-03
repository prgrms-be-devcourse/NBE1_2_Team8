package org.prgrms.devconnect.api.service.interest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.prgrms.devconnect.domain.define.fixture.BoardFixture.createBoard;
import static org.prgrms.devconnect.domain.define.fixture.InterestFixture.createInterestBoard;
import static org.prgrms.devconnect.domain.define.fixture.InterestFixture.createInterestBoardRequestDto;
import static org.prgrms.devconnect.domain.define.fixture.MemberFixture.createMember;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.interest.dto.request.InterestBoardRequestDto;
import org.prgrms.devconnect.api.service.board.BoardQueryService;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.interest.InterestException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.interest.entity.InterestBoard;
import org.prgrms.devconnect.domain.define.interest.repository.InterestBoardRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;

@ExtendWith(MockitoExtension.class)
class InterestCommandServiceTest {

  @Mock
  private InterestBoardRepository interestBoardRepository;
  @Mock
  private MemberQueryService memberQueryService;
  @Mock
  private BoardQueryService boardQueryService;
  @Mock
  private InterestQueryService interestQueryService;
  @InjectMocks
  private InterestCommandService interestCommandService;

  private Member member;
  private Board board;
  private InterestBoard interestBoard;

  @BeforeEach
  void setup() {
    member = createMember("test");
    board = createBoard(member);
    interestBoard = createInterestBoard(member, board);
  }

  @DisplayName("관심게시글_추가시_유효한_DTO가_주어지면_관심게시글을_생성한다")
  @Test
  void 관심게시글_추가시_유효한_DTO가_주어지면_관심게시글을_생성한다() {
    // given
    Long validMemberId = 1L;
    Long validBoardId = 1L;
    InterestBoardRequestDto dto = createInterestBoardRequestDto(validMemberId, validBoardId);

    when(memberQueryService.getMemberByIdOrThrow(dto.memberId()))
        .thenReturn(member);
    when(boardQueryService.getBoardByIdOrThrow(dto.boardId()))
        .thenReturn(board);
    doNothing().when(interestQueryService).validateDuplicatedInterestBoard(member, board);

    // when
    interestCommandService.addInterestBoard(dto);

    // then
    verify(interestBoardRepository, times(1)).save(any(InterestBoard.class));
  }

  @DisplayName("관심게시글_추가시_중복게시글이_주어지면_에러가_발생한다")
  @Test
  void 관심게시글_추가시_중복게시글이_주어지면_에러가_발생한다() {
    // given
    Long validMemberId = 1L;
    Long validBoardId = 1L;
    InterestBoardRequestDto dto = createInterestBoardRequestDto(validMemberId, validBoardId);

    when(memberQueryService.getMemberByIdOrThrow(dto.memberId()))
        .thenReturn(member);
    when(boardQueryService.getBoardByIdOrThrow(dto.boardId()))
        .thenReturn(board);
    doThrow(new InterestException(ExceptionCode.DUPLICATED_INTEREST_BOARD))
        .when(interestQueryService).validateDuplicatedInterestBoard(member, board);
    // when & then
    assertThatThrownBy(
        () -> interestCommandService.addInterestBoard(dto))
        .isInstanceOf(InterestException.class)
        .hasMessage(ExceptionCode.DUPLICATED_INTEREST_BOARD.getMessage());
  }

  @DisplayName("관심게시글_삭제시_유효한_멤버아이디와_게시글아이디가_주어지면_관심게시글을_삭제한다")
  @Test
  void 관심게시글_삭제시_유효한_멤버아이디와_게시글아이디가_주어지면_관심게시글을_삭제한다() {
    // given
    Long validMemberId = 1L;
    Long validBoardId = 1L;

    when(memberQueryService.getMemberByIdOrThrow(validMemberId))
        .thenReturn(member);
    when(boardQueryService.getBoardByIdOrThrow(validBoardId))
        .thenReturn(board);
    when(interestQueryService.getInterestBoardByMemberAndBoardOrThrow(member, board))
        .thenReturn(interestBoard);
    // when
    interestCommandService.removeInterestBoard(validMemberId, validBoardId);

    // then
    verify(interestBoardRepository, times(1)).delete(interestBoard);

  }

  @DisplayName("관심게시글_삭제시_관심게시글이_존재하지않으면_에러가_발생한다")
  @Test
  void 관심게시글_삭제시_관심게시글이_존재하지않으면_에러가_발생한다() {
    // given
    Long validMemberId = 1L;
    Long validBoardId = 1L;

    when(memberQueryService.getMemberByIdOrThrow(validMemberId))
        .thenReturn(member);
    when(boardQueryService.getBoardByIdOrThrow(validBoardId))
        .thenReturn(board);
    doThrow(new InterestException(ExceptionCode.NOT_FOUND_INTEREST_BOARD))
        .when(interestQueryService).getInterestBoardByMemberAndBoardOrThrow(member, board);
    // when & then
    assertThatThrownBy(
        () -> interestCommandService.removeInterestBoard(validMemberId, validBoardId))
        .isInstanceOf(InterestException.class)
        .hasMessage(ExceptionCode.NOT_FOUND_INTEREST_BOARD.getMessage());
  }
}