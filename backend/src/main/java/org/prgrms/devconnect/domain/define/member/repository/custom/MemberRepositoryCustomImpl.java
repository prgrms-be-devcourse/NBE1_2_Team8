package org.prgrms.devconnect.domain.define.member.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.domain.define.member.entity.QMember;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public String findNickNameById(Long memberId) {
    QMember member = QMember.member;
    return queryFactory.select(member.nickname)
            .from(member)
            .where(member.memberId.eq(memberId))
            .fetchOne();
  }
}
