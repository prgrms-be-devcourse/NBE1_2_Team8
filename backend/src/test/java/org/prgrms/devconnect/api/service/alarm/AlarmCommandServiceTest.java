package org.prgrms.devconnect.api.service.alarm;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.alarm.dto.response.AlarmsGetResponse;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberCreateRequestDto;
import org.prgrms.devconnect.api.service.comment.CommentCommandService;
import org.prgrms.devconnect.api.service.member.MemberCommandService;
import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;
import org.prgrms.devconnect.domain.define.alarm.repository.AlarmRepository;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.Comment;
import org.prgrms.devconnect.domain.define.interest.entity.InterestBoard;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class AlarmCommandServiceTest {


  @MockBean
  private MemberCommandService memberCommandService;  // MockBean으로 변경

  @MockBean
  private AlarmQueryService alarmQueryService;

  @MockBean
  private AlarmRepository alarmRepository;

  @MockBean
  private CommentCommandService commentCommandService;

  @SpyBean
  private AlarmCommandService alarmCommandService;

  @Test
  @DisplayName("회원가입 시 웰컴 메시지로직이 실행되는지 검증")
  void verifyWelcomeMessageIsSavedOnSignUp() {

    MemberCreateRequestDto memberCreateRequestDto = mock(MemberCreateRequestDto.class);
    Member member = mock(Member.class);

    when(memberCommandService.createMember(memberCreateRequestDto)).thenReturn(member);
    alarmCommandService.createWelcomeAlarmWhenSignIn(member);

    verify(alarmRepository, times(1)).save(any(Alarm.class));
  }

  @Test
  @DisplayName("알림 전체 삭제_정상 처리되는지 검증")
  void verifyDeleteAlarmsWhenProperRequest() {

    Member member = mock(Member.class);
    Long memberId = 1L;
    AlarmsGetResponse response = mock(AlarmsGetResponse.class);

    doReturn(response).when(alarmQueryService).getAlarmsByMemberIdOrThrow(1L);

    alarmCommandService.deleteAlarmsByMemberId(1L);

    verify(alarmRepository, only()).deleteAllByMemberMemberId(1L);
  }

  @Test
  @DisplayName("알림 단일 삭제")
  void deleteAlarm() {
    Alarm alarm = mock();
    Member member = mock();

    Optional<Alarm> alarmOptional = Optional.of(alarm);

    when(alarm.getAlarmId()).thenReturn(1L);
    when(alarm.getMember()).thenReturn(member);
    when(member.getMemberId()).thenReturn(1L);
    when(alarmQueryService.getAlarmByAlarmIdAndMemberIdOrThrow(1L, 1L)).thenReturn(alarm);

    alarmCommandService.deleteAlarmByAlarmIdAndMemberId(alarm.getAlarmId(), alarm.getMember().getMemberId());

    verify(alarmRepository, times(1)).deleteByAlarmIdAndMemberMemberId(anyLong(), anyLong());
  }

  @Test
  @DisplayName("게시물이 작성된 후, (부모) 댓글이 달렸을 때 알림이 생성 되는지 테스트")
  void createCommentPostedMessageToBoardPoster() {
    Comment comment = mock();
    Board board = mock();
    Member postMember = mock();
    Member commentMember = mock();

    when(commentCommandService.createComment(mock())).thenReturn(comment);
    when(comment.getBoard()).thenReturn(board);
    when(comment.getBoard().getMember()).thenReturn(postMember);
    when(comment.getMember()).thenReturn(commentMember);

    Alarm alarm = alarmCommandService.createCommentPostedMessageToBoardPoster(comment);

    verify(alarmRepository, times(1)).save(any(Alarm.class));
  }

  @Test
  @DisplayName("게시물에 작성된 댓들의 답글이 달렸을 때, 알림이 생성되는 지 테스트")
  void createReplyCommentReceivedAlarmToParentCommenter() {
    Comment parent = mock();
    Comment reply = mock();
    Member parentMember = mock();
    Member replyMember = mock();

    when(reply.getParent()).thenReturn(parent);
    when(reply.getParent().getMember()).thenReturn(parentMember);
    when(reply.getMember()).thenReturn(replyMember);
    when(commentCommandService.createComment(mock())).thenReturn(reply);

    Alarm alarm = alarmCommandService.createReplyCommentReceivedAlarmToParentCommenter(reply);

    verify(alarmRepository,times(1)).save(any(Alarm.class));
  }


  @Test
  @DisplayName("마감 임박 알림 저장 되는지 확인")
  void createUrgentAlarmAboutInterestBoard() {
    InterestBoard interestBoard = mock();
    Member member = mock();
    Board board = mock();

    when(board.getEndDate()).thenReturn(LocalDateTime.MAX);
    when(interestBoard.getMember()).thenReturn(member);
    when(interestBoard.getBoard()).thenReturn(board);

    alarmCommandService.createUrgentAlarmAboutInterestBoard(interestBoard);

    verify(alarmRepository, times(1)).save(any(Alarm.class));
  }
}
