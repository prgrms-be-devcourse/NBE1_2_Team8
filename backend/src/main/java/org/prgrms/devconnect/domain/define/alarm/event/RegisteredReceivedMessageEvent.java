package org.prgrms.devconnect.domain.define.alarm.event;

import org.prgrms.devconnect.api.controller.chatting.dto.response.MessageResponse;

public record RegisteredReceivedMessageEvent(MessageResponse messageResponse) {
}
