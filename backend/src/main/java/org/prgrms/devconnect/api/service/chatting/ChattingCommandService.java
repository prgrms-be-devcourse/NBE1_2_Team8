package org.prgrms.devconnect.api.service.chatting;


import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatPartResponse;
import org.prgrms.devconnect.api.controller.chatting.dto.response.MessageResponse;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.chatting.ChattingException;
import org.prgrms.devconnect.domain.define.chatting.entity.ChatParticipation;
import org.prgrms.devconnect.domain.define.chatting.entity.ChattingRoom;
import org.prgrms.devconnect.domain.define.chatting.entity.Message;
import org.prgrms.devconnect.domain.define.chatting.entity.constant.ChattingRoomStatus;
import org.prgrms.devconnect.domain.define.chatting.repository.ChatParticipationRepository;
import org.prgrms.devconnect.domain.define.chatting.repository.ChattingRoomRepository;
import org.prgrms.devconnect.domain.define.chatting.repository.MessageRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChattingCommandService {

  private final ChattingRoomRepository chattingRoomRepository;
  private final ChatParticipationRepository chatParticipationRepository;
  private final MessageRepository messageRepository;
  private final MemberQueryService memberQueryService;
  private final ChattingQueryService chattingQueryService;

  /*
    새로운 채팅방을 생성하는 서비스 코드
    1대1 대화를 시작할 때 필요한 사용자 2명의 ID를 가져와서 처리
  */
  public ChatPartResponse createNewChatting(Long sendMemberId, Long receiveMemberId){
    Member sender = memberQueryService.getMemberByIdOrThrow(sendMemberId);
    Member receiver = memberQueryService.getMemberByIdOrThrow(receiveMemberId);

    //새로운 채팅방 생성
    ChattingRoom chattingRoom = new ChattingRoom(ChattingRoomStatus.ACTIVE);
    chattingRoomRepository.save(chattingRoom);

    // 채팅 참여 객체 생성 및 채팅방 연결
    ChatParticipation senderChatPart = ChatParticipation.builder()
            .member(sender)
            .chattingRoom(chattingRoom)
            .build();
    ChatParticipation receiverChatPart = ChatParticipation
            .builder()
            .member(receiver)
            .chattingRoom(chattingRoom)
            .build();

    chatParticipationRepository.save(senderChatPart);
    chatParticipationRepository.save(receiverChatPart);

    //chatpartId, roomId 반환
    return new ChatPartResponse(senderChatPart.getChatPartId(), chattingRoom.getRoomId());
  }

  // 채팅방 참여 메서드
  public ChatPartResponse joinChatRoom(Long memberId, Long chatroomId){
    // 연관관계 매핑 준비
    Member member = memberQueryService.getMemberByIdOrThrow(memberId);
    ChattingRoom chattingRoom = chattingQueryService.getChatRoomById(chatroomId);

    //이미 참여했다면 오류 발생
    chatParticipationRepository.findByMember_MemberIdAndChattingRoom_RoomId(memberId, chatroomId)
            .ifPresent(chatParticipation -> {throw new ChattingException(ExceptionCode.ALREADY_JOINED_CHATROOM);});

    //채팅 참여 엔티티 생성
    ChatParticipation chatParticipation = ChatParticipation.builder()
            .member(member)
            .chattingRoom(chattingRoom)
            .build();

    chatParticipationRepository.save(chatParticipation);

    return new ChatPartResponse(chatParticipation.getChatPartId(), chattingRoom.getRoomId());
  }

  // 메세지를 저장하는 메서드
  public MessageResponse sendMessage(Long chatpartId, String content){
    // 채팅 참여 엔티티 조회
    ChatParticipation chatParticipation = chatParticipationRepository.findById(chatpartId)
            .orElseThrow(() -> new ChattingException(ExceptionCode.NOT_FOUND_CHATPART));

    // 메세지 생성
    Message message = Message.builder()
            .chatParticipation(chatParticipation)
            .content(content)
            .build();

    messageRepository.save(message);

    // 닉네임 조회
    String nickname = chatParticipation.getMember().getNickname();

    return MessageResponse.builder()
            .messageId(message.getMessageId())
            .senderId(chatParticipation.getMember().getMemberId())
            .nickname(nickname)
            .content(message.getContent())
            .createdAt(message.getCreatedAt())
            .build();
  }

    public void leaveChatRoom(Long chatpartId){
    chatParticipationRepository.deleteById(chatpartId);
  }



  // 채팅방 비활성화 서비스
  public void closeChattingRoom(Long chatroomId){
    ChattingRoom chattingRoom = chattingQueryService.getChatRoomById(chatroomId);
    chattingRoom.closeChatRoom();
    chattingRoomRepository.save(chattingRoom);
  }
}
