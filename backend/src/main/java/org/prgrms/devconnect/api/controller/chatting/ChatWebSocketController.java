package org.prgrms.devconnect.api.controller.chatting;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.chatting.dto.request.JoinRequest;
import org.prgrms.devconnect.api.controller.chatting.dto.request.MessageRequest;
import org.prgrms.devconnect.api.controller.chatting.dto.response.MessageResponse;
import org.prgrms.devconnect.api.service.chatting.ChattingCommandService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
  private final SimpMessagingTemplate messagingTemplate;
  private final ChattingCommandService chattingCommandService;

  @MessageMapping("/chat/{roomId}")
  @SendTo("/topic/room/{roomId}")
  public void sendMessage(@DestinationVariable("roomId") Long roomId , MessageRequest messageRequest) {
    // 메시지 저장 처리
    MessageResponse response = chattingCommandService.sendMessage(messageRequest.chatpartId(), messageRequest.content());

    // 저장된 메시지를 WebSocket을 통해 모든 구독자에게 브로드캐스트
    messagingTemplate.convertAndSend("/topic/room/" + roomId, response);
  }

  @MessageMapping("/chat/join")
  public void joinChatRoom(JoinRequest joinRoomRequest) {
    // 사용자의 채팅방 참여 처리
    chattingCommandService.joinChatRoom(joinRoomRequest.memberId(), joinRoomRequest.chatroomId());
  }

  // 사용자가 채팅방을 나갈 때 처리하는 메서드
  @MessageMapping("/chat/leave/{chatpartId}")
  public void leaveChatRoom(@DestinationVariable("chatpartId") Long chatpartId) {
    // 사용자의 채팅방 나가기 처리
    chattingCommandService.leaveChatRoom(chatpartId);
  }
}
