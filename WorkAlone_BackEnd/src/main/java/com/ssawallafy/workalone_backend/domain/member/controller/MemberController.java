package com.ssawallafy.workalone_backend.domain.member.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssawallafy.workalone_backend.domain.member.dto.MemberModifyReq;
import com.ssawallafy.workalone_backend.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PutMapping("/update")
	public ResponseEntity<?> modifyMember(@RequestBody MemberModifyReq memberModifyReq) {

		Long memberId = 1L;
		memberService.updateMember(memberId, memberModifyReq);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteMember() {

		long memberId = 1L;

		memberService.removeMember(memberId);

		return new ResponseEntity<>(null, NO_CONTENT);
	}
}
