package com.ssafy.workalone.global.wrapper

import com.ssafy.workalone.data.model.member.LocalMember
import com.ssafy.workalone.data.model.member.Member

fun MemberWrapper(member: Member): LocalMember {
    return LocalMember(
        memberId = member.memberId,
        memberName = member.memberName,
        memberWeight = member.memberWeight
    )
}