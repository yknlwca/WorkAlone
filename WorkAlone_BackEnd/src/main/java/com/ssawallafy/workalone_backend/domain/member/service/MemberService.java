package com.ssawallafy.workalone_backend.domain.member.service;

import com.ssawallafy.workalone_backend.domain.member.dto.MemberModifyReq;
import com.ssawallafy.workalone_backend.domain.member.dto.MemberSaveReq;
import com.ssawallafy.workalone_backend.domain.member.entity.Member;

public interface MemberService {
	void updateMember(Long memberId, MemberModifyReq memberModifyReq);

	void removeMember(Long memberId);

	long saveMember(MemberSaveReq memberSaveReq);

	Member findMember(String name);
}
