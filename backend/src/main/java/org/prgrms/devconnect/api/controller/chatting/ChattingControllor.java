package org.prgrms.devconnect.api.controller.chatting;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatRoomResponse;
import org.prgrms.devconnect.api.service.chatting.ChattingCommandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChattingControllor {

  private final ChattingCommandService chattingCommandService;

  @PostMapping("/{memberId}")
  public ResponseEntity<ChatRoomResponse> createChatting(@PathVariable @Valid Long memberId, @RequestBody @Valid Long receiverId){
    Long chatroomId = chattingCommandService.createNewChatting(memberId, receiverId);
    return ResponseEntity.status(HttpStatus.OK).body(new ChatRoomResponse(chatroomId));
  }

  @PutMapping("/{chatroomId}")
  public ResponseEntity<Void> closeChattingRoom(@PathVariable @Valid Long chatroomId){
    chattingCommandService.closeChattingRoom(chatroomId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
