package org.prgrms.devconnect.domain.define.chatting.repository.custom;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatRoomListResponse;
import org.prgrms.devconnect.api.controller.chatting.dto.response.MessageResponse;
import org.prgrms.devconnect.domain.define.chatting.entity.QChatParticipation;
import org.prgrms.devconnect.domain.define.chatting.entity.QChattingRoom;
import org.prgrms.devconnect.domain.define.chatting.entity.QMessage;
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
                    ChatRoomListResponse.class,
                    chatpart.member.memberId,
                    chatpart.chatPartId,
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
                    chatpart.chatPartId,
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

    // 전체 레코드 수 계산
    long total = queryFactory.select(chattingRoom.count())
            .from(chattingRoom)
            .join(chatpart.chattingRoom, chattingRoom)
            .where(chatpart.member.memberId.eq(memberId)
                    .and(chattingRoom.status.eq(ChattingRoomStatus.ACTIVE)))
            .fetchOne();

    return new PageImpl<>(content, pageable, total);
  }

  @Override
  public Page<MessageResponse> findAllMessageByRoomId(Long roomId, Pageable pageable) {
    QChatParticipation chatpart = QChatParticipation.chatParticipation;
    QMessage message = QMessage.message;

    List<MessageResponse> messages = queryFactory.select(Projections.constructor(MessageResponse.class,
                    message.messageId,
                    chatpart.member.memberId,
                    message.content,
                    message.createdAt))
            .from(message)
            .join(chatpart.messages, message)
            .where(chatpart.chattingRoom.roomId.eq(roomId))
            .orderBy(message.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    // 전체 레코드 수 계산
    long total = queryFactory.select(message.count())
            .from(message)
            .join(chatpart.messages, message)
            .where(chatpart.chattingRoom.roomId.eq(roomId))
            .fetchOne();

    return new PageImpl<>(messages, pageable, total);
  }
}
