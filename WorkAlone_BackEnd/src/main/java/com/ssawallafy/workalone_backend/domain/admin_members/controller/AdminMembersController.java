package com.ssawallafy.workalone_backend.domain.admin_members.controller;

import com.ssawallafy.workalone_backend.domain.admin_members.dto.ReadVideoRes;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssawallafy.workalone_backend.domain.admin_members.dto.AdminMemberListDto;
import com.ssawallafy.workalone_backend.domain.admin_members.service.AdminMembersService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/members")
@RequiredArgsConstructor
@Tag(name = "Admin Member", description = "admin member api")
public class AdminMembersController {

	private final AdminMembersService adminMemberService;

	@GetMapping
	public ResponseEntity<AdminMemberListDto> readMemberList(@RequestParam("organizationId") Long organizationId){

		AdminMemberListDto res = adminMemberService.readMemberList(organizationId);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/video")
	@Operation(summary = "운동영상 저장", description = "회원의 운동 영상을 조회합니다.")
	public ResponseEntity<ReadVideoRes> readVideoList(@RequestParam Long memberId){

		ReadVideoRes res = adminMemberService.readVideo(memberId);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
