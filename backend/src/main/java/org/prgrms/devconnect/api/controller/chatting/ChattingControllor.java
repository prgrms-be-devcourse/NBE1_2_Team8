package org.prgrms.devconnect.api.controller.chatting;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.chatting.dto.request.ChatRoomRequest;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatPartResponse;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatRoomListResponse;
import org.prgrms.devconnect.api.controller.chatting.dto.response.MessageFullResponse;
import org.prgrms.devconnect.api.service.chatting.ChattingCommandService;
import org.prgrms.devconnect.api.service.chatting.ChattingQueryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
@Tag(name = "채팅 API", description = "채팅 관련 기능을 제공하는 API")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
})
public class ChattingControllor {

  private final ChattingCommandService chattingCommandService;
  private final ChattingQueryService chattingQueryService;

  @PostMapping("/member/{memberId}")
  @Operation(summary = "새로운 채팅방 생성", description = "특정 사용자와의 새로운 채팅방 생성", parameters = {
          @Parameter(name = "memberId", description = "멤버 ID", required = true, example = "1")
  })
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "채팅방 생성 성공"),
          @ApiResponse(responseCode = "404", description = "엔티티 NOT FOUND"),
  })
  public ResponseEntity<ChatPartResponse> createChatting(@PathVariable("memberId") Long memberId,
                                                         @RequestBody @Valid ChatRoomRequest request) {
    ChatPartResponse chatting = chattingCommandService.createNewChatting(memberId, request.receiverId());
    return ResponseEntity.status(CREATED).body(chatting);
  }

  @GetMapping("/member/{memberId}")
  @Operation(summary = "활성화된 채팅 방 조회", description = "멤버의 모든 활성화된 채팅 방을 조회", parameters = {
          @Parameter(name = "memberId", description = "멤버 ID", required = true, example = "1")
  })
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "채팅방 조회 성공"),
          @ApiResponse(responseCode = "404", description = "엔티티 NOT FOUND"),
  })
  public ResponseEntity<List<ChatRoomListResponse>> createChatting(@PathVariable("memberId") Long memberId) {
    List<ChatRoomListResponse> results = chattingQueryService.findAllActivateChattingsByMemberId(memberId);
    return ResponseEntity.status(OK).body(results);
  }

  @PutMapping("/{chatroomId}")
  @Operation(summary = "채팅방 비활성화", description = "특정 채팅방을 종료", parameters = {
          @Parameter(name = "chatroomId", description = "채팅방 ID", required = true, example = "1")
  })
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "채팅방 종료 성공"),
          @ApiResponse(responseCode = "404", description = "엔티티 NOT FOUND"),
  })
  public ResponseEntity<Void> closeChattingRoom(@PathVariable("chatroomId") Long chatroomId) {
    chattingCommandService.closeChattingRoom(chatroomId);
    return ResponseEntity.status(NO_CONTENT).build();
  }

  @GetMapping("/rooms/{roomId}/messages")
  @Operation(summary = "채팅 메세지 조회", description = "특정 채팅방의 메세지를 페이징하여 조회", parameters = {
          @Parameter(name = "roomId", description = "채팅방 ID", required = true, example = "1")
  })
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "채팅 메세지 조회 성공"),
          @ApiResponse(responseCode = "404", description = "엔티티 NOT FOUND"),
  })
  public ResponseEntity<MessageFullResponse> getMessages(
          @PathVariable("roomId") Long roomId,
          @PageableDefault(size = 20) Pageable pageable) {

    // 서비스 레이어에서 메시지를 조회
    MessageFullResponse messages = chattingQueryService.getAllMessagebyRoomId(roomId, pageable);
    return ResponseEntity.status(OK).body(messages);
  }
}
