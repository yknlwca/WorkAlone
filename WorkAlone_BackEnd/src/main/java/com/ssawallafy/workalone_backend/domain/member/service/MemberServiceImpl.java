package com.ssawallafy.workalone_backend.domain.member.service;

import static com.ssawallafy.workalone_backend.domain.member.exception.ErrorCode.*;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssawallafy.workalone_backend.domain.member.dto.MemberModifyReq;
import com.ssawallafy.workalone_backend.domain.member.dto.MemberSaveReq;
import com.ssawallafy.workalone_backend.domain.member.entity.Member;
import com.ssawallafy.workalone_backend.domain.member.exception.BusinessLogicException;
import com.ssawallafy.workalone_backend.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	@Override
	public long saveMember(MemberSaveReq memberSaveReq) {

		Member member = Member.builder()
			.name(memberSaveReq.getName())
			.weight(memberSaveReq.getWeight())
			.build();

		return memberRepository.save(member).getId();
	}

	@Override
	public void updateMember(Long memberId, MemberModifyReq memberModifyReq) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessLogicException(NOT_FOUND));

		Optional.ofNullable(memberModifyReq.getNickname()).ifPresent(member::updateNickname);
		Optional.ofNullable(memberModifyReq.getHeight()).ifPresent(member::updateHeight);
		Optional.ofNullable(memberModifyReq.getWeight()).ifPresent(member::updateWeight);

		memberRepository.save(member);
	}

	@Override
	public void removeMember(Long memberId) {
		// 다른 repository에서도 deleteById 실행 (순서 주의)
		memberRepository.deleteById(memberId);
	}


}
