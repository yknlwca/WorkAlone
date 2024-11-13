package com.ssawallafy.workalone_backend.domain.admin_members.dto;

import java.util.List;

import com.ssawallafy.workalone_backend.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AdminMemberListDto {

	private List<Member> memberList;

}
