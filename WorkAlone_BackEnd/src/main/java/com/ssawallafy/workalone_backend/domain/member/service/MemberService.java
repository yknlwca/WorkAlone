package com.ssawallafy.workalone_backend.domain.member.service;

import com.ssawallafy.workalone_backend.domain.member.dto.MemberModifyReq;
import com.ssawallafy.workalone_backend.domain.member.dto.MemberSaveReq;
import com.ssawallafy.workalone_backend.domain.member.dto.MemberUpdateReq;
import com.ssawallafy.workalone_backend.domain.member.entity.Member;

public interface MemberService {
	// void updateMember(Long memberId, MemberModifyReq memberModifyReq);

	void removeMember(Long memberId);

	Member saveMember(MemberSaveReq memberSaveReq);

	Member findMember(String name);

	Member modifyMember(MemberUpdateReq memberUpdateReq);
}
