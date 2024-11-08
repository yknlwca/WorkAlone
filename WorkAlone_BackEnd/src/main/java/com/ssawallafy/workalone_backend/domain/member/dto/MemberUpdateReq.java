package com.ssawallafy.workalone_backend.domain.member.dto;

import lombok.Getter;

@Getter
public class MemberUpdateReq {

	private long memberId;

	private Boolean isRecording;
}
