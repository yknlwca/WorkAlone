package com.ssawallafy.workalone_backend.domain.member.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssawallafy.workalone_backend.domain.member.dto.MemberModifyReq;
import com.ssawallafy.workalone_backend.domain.member.dto.MemberSaveReq;
import com.ssawallafy.workalone_backend.domain.member.dto.MemberSaveRes;
import com.ssawallafy.workalone_backend.domain.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Member", description = "member api")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/save")
	@Operation(summary = "회원정보 저장", description = "회원의 이름, 몸무게를 저장")
	public ResponseEntity<MemberSaveRes> saveMember(@RequestBody MemberSaveReq memberSaveReq) {

		long memberId = memberService.saveMember(memberSaveReq);

		MemberSaveRes res = MemberSaveRes.builder()
			.member_id(memberId)
			.build();

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("/update")
	@Operation(summary = "회원정보 수정", description = "회원의 닉네임, 키, 몸무게를 수정")
	public ResponseEntity<?> modifyMember(@RequestBody MemberModifyReq memberModifyReq) {

		Long memberId = 1L;
		memberService.updateMember(memberId, memberModifyReq);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@DeleteMapping
	@Operation(summary = "회원 탈퇴", description = "회원을 DB에서 삭제합니다.")
	public ResponseEntity<?> deleteMember() {

		long memberId = 1L;

		memberService.removeMember(memberId);

		return new ResponseEntity<>(null, NO_CONTENT);
	}
}
