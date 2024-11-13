package com.ssawallafy.workalone_backend.domain.admin_members.service;

import com.ssawallafy.workalone_backend.domain.admin_members.dto.AdminMemberListDto;

public interface AdminMembersService {
	AdminMemberListDto readMemberList(Long organizationId);
}
