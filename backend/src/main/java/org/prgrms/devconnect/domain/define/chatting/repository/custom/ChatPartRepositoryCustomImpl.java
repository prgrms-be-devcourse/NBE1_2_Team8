package org.prgrms.devconnect.domain.define.chatting.repository.custom;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatRoomListResponse;
import org.prgrms.devconnect.domain.define.chatting.entity.QChatParticipation;
import org.prgrms.devconnect.domain.define.chatting.entity.QChattingRoom;
import org.prgrms.devconnect.domain.define.chatting.entity.constant.ChattingRoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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

  @Override
  public Page<ChatRoomListResponse> findByMemberIdAndStatusIsActiveWithPage(Long memberId, Pageable pageable) {
    QChatParticipation chatpart = QChatParticipation.chatParticipation;
    QChattingRoom chattingRoom = QChattingRoom.chattingRoom;

    List<ChatRoomListResponse> content = queryFactory.select(Projections.constructor(
                    ChatRoomListResponse.class,
                    chatpart.member.memberId,
                    chattingRoom.roomId,
                    chattingRoom.status
            ))
            .from(chatpart)
            .join(chatpart.chattingRoom, chattingRoom)
            .where(chatpart.member.memberId.eq(memberId)
                    .and(chattingRoom.status.eq(ChattingRoomStatus.ACTIVE)))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy()
            .fetch();

    long total = queryFactory.select(chattingRoom.count())
            .from(chatpart)
            .join(chatpart.chattingRoom, chattingRoom)
            .where(chatpart.member.memberId.eq(memberId)
                    .and(chattingRoom.status.eq(ChattingRoomStatus.ACTIVE)))
            .fetchOne();

    return new PageImpl<>(content, pageable, total);
  }
}
