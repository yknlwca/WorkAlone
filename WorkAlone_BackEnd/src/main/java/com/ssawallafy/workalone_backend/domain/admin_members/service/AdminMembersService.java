package com.ssawallafy.workalone_backend.domain.admin_members.service;

import com.ssawallafy.workalone_backend.domain.admin_members.dto.AdminMemberListDto;
import com.ssawallafy.workalone_backend.domain.admin_members.dto.ReadVideoRes;

public interface AdminMembersService {
	AdminMemberListDto readMemberList(Long organizationId);

    ReadVideoRes readVideo(Long memberId);
}
