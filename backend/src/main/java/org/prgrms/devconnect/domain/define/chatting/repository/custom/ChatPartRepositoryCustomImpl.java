package org.prgrms.devconnect.domain.define.chatting.repository.custom;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatRoomListResponse;
import org.prgrms.devconnect.domain.define.chatting.entity.QChatParticipation;
import org.prgrms.devconnect.domain.define.chatting.entity.QChattingRoom;
import org.prgrms.devconnect.domain.define.chatting.entity.constant.ChattingRoomStatus;

import java.util.List;

@RequiredArgsConstructor
public class ChatPartRepositoryCustomImpl implements ChatPartRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  @Override
  public List<ChatRoomListResponse> findByMemberIdAndStatusIsActive(Long memberId) {
    QChatParticipation chatpart = QChatParticipation.chatParticipation;
    QChattingRoom chattingRoom = QChattingRoom.chattingRoom;

    return queryFactory.select(Projections.constructor(
                    ChatRoomListResponse.class ,
                    chatpart.member.memberId,
                    chattingRoom.roomId,
                    chattingRoom.status
            ))
            .from(chatpart)
            .join(chatpart.chattingRoom, chattingRoom)
            .where(chatpart.member.memberId.eq(memberId).and(chattingRoom.status.eq(ChattingRoomStatus.ACTIVE)))
            .fetch();
  }
}
