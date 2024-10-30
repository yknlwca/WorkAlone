package com.ssawallafy.workalone_backend.domain.member.service;

import com.ssawallafy.workalone_backend.domain.member.dto.MemberModifyReq;

public interface MemberService {
	void updateMember(Long memberId, MemberModifyReq memberModifyReq);

	void removeMember(Long memberId);
}
