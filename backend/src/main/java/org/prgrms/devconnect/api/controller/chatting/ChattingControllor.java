package org.prgrms.devconnect.api.controller.chatting;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.chatting.dto.request.ChatRoomRequest;
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
  public ResponseEntity<ChatRoomResponse> createChatting(@PathVariable("memberId") Long memberId,
                                                         @RequestBody @Valid ChatRoomRequest request){
    Long chatroomId = chattingCommandService.createNewChatting(memberId, request.receiver_id());
    return ResponseEntity.status(HttpStatus.OK).body(new ChatRoomResponse(chatroomId));
  }

  @PutMapping("/{chatroomId}")
  public ResponseEntity<Void> closeChattingRoom(@PathVariable("chatroomId") Long chatroomId){
    chattingCommandService.closeChattingRoom(chatroomId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
