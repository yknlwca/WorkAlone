package com.ssawallafy.workalone_backend.domain.admin_members.service;

import java.util.ArrayList;
import java.util.List;

import com.ssawallafy.workalone_backend.domain.admin_members.dto.ReadVideoRes;
import com.ssawallafy.workalone_backend.domain.admin_members.dto.ReadVideoResDetail;
import com.ssawallafy.workalone_backend.domain.summary.repository.ExerciseSummaryRepository;
import org.springframework.stereotype.Service;

import com.ssawallafy.workalone_backend.domain.admin_members.dto.AdminMemberListDto;
import com.ssawallafy.workalone_backend.domain.member.entity.Member;
import com.ssawallafy.workalone_backend.domain.member.repository.MemberRepository;
import com.ssawallafy.workalone_backend.domain.organization.entity.OrganizationMapping;
import com.ssawallafy.workalone_backend.domain.organization.repository.OrganizationMappingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminMemberServiceImpl implements AdminMembersService {

	private final MemberRepository memberRepository;
	private final OrganizationMappingRepository organizationMappingRepository;
	private final ExerciseSummaryRepository exerciseSummaryRepository;

	@Override
	public AdminMemberListDto readMemberList(Long organizationId) {

		List<OrganizationMapping> mappingList = organizationMappingRepository.findByOrganizationId(organizationId);

		List<Member> memberList = new ArrayList<>();
		for (OrganizationMapping mapping : mappingList) {
			Member m = mapping.getMember();
			// 트레이너는 제외
			if (!m.getIs_trainer())
				memberList.add(mapping.getMember());
		}

		AdminMemberListDto res = AdminMemberListDto.builder()
			.memberList(memberList)
			.build();

		return res;
	}

	@Override
	public ReadVideoRes readVideo(Long memberId) {

		// memberId에 해당하는 운동기록 모두 불러오기
		List<ReadVideoResDetail> videoList = exerciseSummaryRepository.findAllAndTitleByMemberId(memberId);

		ReadVideoRes res = ReadVideoRes.builder()
				.videoList(videoList)
				.build();

		return res;
	}
}
