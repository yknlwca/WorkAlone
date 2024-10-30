package com.ssawallafy.workalone_backend.domain.member.exception;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException{

	private final ErrorCode errorCode;

	public BusinessLogicException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

}