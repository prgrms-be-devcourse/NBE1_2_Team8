package org.prgrms.devconnect.api.service.chatting;


import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.chatting.ChattingException;
import org.prgrms.devconnect.domain.define.chatting.entity.ChatParticipation;
import org.prgrms.devconnect.domain.define.chatting.entity.ChattingRoom;
import org.prgrms.devconnect.domain.define.chatting.entity.constant.ChattingRoomStatus;
import org.prgrms.devconnect.domain.define.chatting.entity.repository.ChatParticipationRepository;
import org.prgrms.devconnect.domain.define.chatting.entity.repository.ChattingRoomRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChattingCommandService {

  private final ChattingRoomRepository chattingRoomRepository;
  private final ChatParticipationRepository chatParticipationRepository;
  private final MemberQueryService memberQueryService;

  /*
    새로운 채팅방을 생성하는 서비스 코드
    1대1 대화를 시작할 때 필요한 사용자 2명의 ID를 가져와서 처리

  */
  public Long createNewChatting(Long sendMemberId, Long receiveMemberId){
    Member sender = memberQueryService.getMemberByIdOrThrow(sendMemberId);
    Member receivier = memberQueryService.getMemberByIdOrThrow(sendMemberId);

    //새로운 채팅방 생성
    ChattingRoom chattingRoom = new ChattingRoom(ChattingRoomStatus.ACTIVE);
    chattingRoomRepository.save(chattingRoom);

    // 채팅 참여 객체 생성 및 채팅방 연결
    ChatParticipation senderChatPart = ChatParticipation.builder().chattingRoom(chattingRoom).build();
    ChatParticipation receiverChatPart = ChatParticipation.builder().chattingRoom(chattingRoom).build();
    sender.addChattings(senderChatPart);
    receivier.addChattings(receiverChatPart);

    chatParticipationRepository.save(senderChatPart);
    chatParticipationRepository.save(receiverChatPart);

    return chattingRoom.getRoomId();
  }

  // 채팅방 비활성화 서비스
  public void closeChattingRoom(Long chatroomId){
    ChattingRoom chattingRoom = chattingRoomRepository.findById(chatroomId)
            .orElseThrow(() -> new ChattingException(ExceptionCode.NOT_FOUND_CHATROOM));
    chattingRoom.closeChatRoom();
    chattingRoomRepository.save(chattingRoom);
  }
}
