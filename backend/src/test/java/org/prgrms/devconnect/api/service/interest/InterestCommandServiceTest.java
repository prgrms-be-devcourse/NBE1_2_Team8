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
import static org.prgrms.devconnect.domain.define.fixture.InterestFixture.createInterestJobPost;
import static org.prgrms.devconnect.domain.define.fixture.InterestFixture.createInterestJobPostRequestDto;
import static org.prgrms.devconnect.domain.define.fixture.JobPostFixture.createJobPost;
import static org.prgrms.devconnect.domain.define.fixture.MemberFixture.createMember;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.interest.dto.request.InterestBoardRequestDto;
import org.prgrms.devconnect.api.controller.interest.dto.request.InterestJobPostRequestDto;
import org.prgrms.devconnect.api.service.board.BoardQueryService;
import org.prgrms.devconnect.api.service.jobpost.JobPostQueryService;
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

@ExtendWith(MockitoExtension.class)
class InterestCommandServiceTest {

  @Mock
  private InterestBoardRepository interestBoardRepository;
  @Mock
  private InterestJobPostRepository interestJobPostRepository;
  @Mock
  private MemberQueryService memberQueryService;
  @Mock
  private BoardQueryService boardQueryService;
  @Mock
  private JobPostQueryService jobPostQueryService;
  @Mock
  private InterestQueryService interestQueryService;
  @InjectMocks
  private InterestCommandService interestCommandService;

  private Member member;
  private Board board;
  private JobPost jobPost;
  private InterestBoard interestBoard;
  private InterestJobPost interestJobPost;

  @BeforeEach
  void setup() {
    member = createMember("test");
    board = createBoard(member);
    jobPost = createJobPost();
    interestBoard = createInterestBoard(member, board);
    interestJobPost = createInterestJobPost(member, jobPost);
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

    when(interestQueryService.getInterestBoardByMemberIdAndBoardIdOrThrow(validMemberId,
        validBoardId))
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

    doThrow(new InterestException(ExceptionCode.NOT_FOUND_INTEREST_BOARD))
        .when(interestQueryService)
        .getInterestBoardByMemberIdAndBoardIdOrThrow(validMemberId, validBoardId);
    // when & then
    assertThatThrownBy(
        () -> interestCommandService.removeInterestBoard(validMemberId, validBoardId))
        .isInstanceOf(InterestException.class)
        .hasMessage(ExceptionCode.NOT_FOUND_INTEREST_BOARD.getMessage());
  }

  @DisplayName("관심채용공고_추가시_유효한_DTO가_주어지면_관심채용공고를_생성한다")
  @Test
  void 관심채용공고_추가시_유효한_DTO가_주어지면_관심채용공고를_생성한다() {
    // given
    Long validMemberId = 1L;
    Long validJobPostId = 1L;
    InterestJobPostRequestDto dto = createInterestJobPostRequestDto(validMemberId, validJobPostId);

    when(memberQueryService.getMemberByIdOrThrow(dto.memberId()))
        .thenReturn(member);
    when(jobPostQueryService.getJobPostByIdOrThrow(dto.jobPostId()))
        .thenReturn(jobPost);
    doNothing().when(interestQueryService).validateDuplicatedInterestJobPost(member, jobPost);

    // when
    interestCommandService.addInterestJobPost(dto);

    // then
    verify(interestJobPostRepository, times(1)).save(any(InterestJobPost.class));
  }

  @DisplayName("관심채용공고_추가시_중복채용공고가_주어지면_에러가_발생한다")
  @Test
  void 관심채용공고_추가시_중복채용공고가_주어지면_에러가_발생한다() {
    // given
    Long validMemberId = 1L;
    Long validJobPostId = 1L;
    InterestJobPostRequestDto dto = createInterestJobPostRequestDto(validMemberId, validJobPostId);

    when(memberQueryService.getMemberByIdOrThrow(dto.memberId()))
        .thenReturn(member);
    when(jobPostQueryService.getJobPostByIdOrThrow(dto.jobPostId()))
        .thenReturn(jobPost);
    doThrow(new InterestException(ExceptionCode.DUPLICATED_INTEREST_JOB_POST))
        .when(interestQueryService).validateDuplicatedInterestJobPost(member, jobPost);

    // when & then
    assertThatThrownBy(
        () -> interestCommandService.addInterestJobPost(dto))
        .isInstanceOf(InterestException.class)
        .hasMessage(ExceptionCode.DUPLICATED_INTEREST_JOB_POST.getMessage());
  }

  @DisplayName("관심채용공고_삭제시_유효한_멤버아이디와_채용공고아이디가_주어지면_관심채용공고를_제거한다")
  @Test
  void 관심채용공고_삭제시_유효한_멤버아이디와_채용공고아이디가_주어지면_관심채용공고를_제거한다() {
    // given
    Long memberId = 1L;
    Long jobPostId = 1L;

    when(interestQueryService.getInterestJobPostByMemberIdAndJobPostIdOrThrow(memberId, jobPostId))
        .thenReturn(interestJobPost);
    // when
    interestCommandService.removeInterestJobPost(memberId, jobPostId);

    // then
    verify(interestJobPostRepository, times(1)).delete(interestJobPost);
  }

  @DisplayName("관심채용공고_삭제시_존재하지않으면_에러가_발생한다")
  @Test
  void 관심채용공고_삭제시_존재하지않으면_에러가_발생한다() {
    // given
    Long memberId = 1L;
    Long jobPostId = 1L;

    when(interestQueryService.getInterestJobPostByMemberIdAndJobPostIdOrThrow(memberId, jobPostId))
        .thenThrow(new InterestException(ExceptionCode.NOT_FOUND_INTEREST_JOB_POST));
    // when & then
    assertThatThrownBy(
        () -> interestCommandService.removeInterestJobPost(memberId, jobPostId))
        .isInstanceOf(InterestException.class)
        .hasMessage(ExceptionCode.NOT_FOUND_INTEREST_JOB_POST.getMessage());
    verify(interestJobPostRepository, times(0)).delete(interestJobPost);
  }
}