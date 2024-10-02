package org.prgrms.devconnect.api.controller.chatting;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.chatting.dto.request.ChatRoomRequest;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatPartResponse;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatRoomListResponse;
import org.prgrms.devconnect.api.service.chatting.ChattingCommandService;
import org.prgrms.devconnect.api.service.chatting.ChattingQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChattingControllor {

  private final ChattingCommandService chattingCommandService;
  private final ChattingQueryService chattingQueryService;

  @PostMapping("/member/{memberId}")
  public ResponseEntity<ChatPartResponse> createChatting(@PathVariable("memberId") Long memberId,
                                                         @RequestBody @Valid ChatRoomRequest request){
    ChatPartResponse chatting = chattingCommandService.createNewChatting(memberId, request.receiverId());
    return ResponseEntity.status(HttpStatus.OK).body(chatting);
  }

  @GetMapping("/member/{memberId}")
  public ResponseEntity<List<ChatRoomListResponse>> createChatting(@PathVariable("memberId") Long memberId){
    List<ChatRoomListResponse> allActivateChattingsByMemberId = chattingQueryService.findAllActivateChattingsByMemberId(memberId);
    return ResponseEntity.status(HttpStatus.OK).body(allActivateChattingsByMemberId);
  }



  @PutMapping("/{chatroomId}")
  public ResponseEntity<Void> closeChattingRoom(@PathVariable("chatroomId") Long chatroomId){
    chattingCommandService.closeChattingRoom(chatroomId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
