package com.ssawallafy.workalone_backend.domain.organization.exception;

import lombok.Getter;

@Getter
public class OrganizationException extends RuntimeException{

	private final ErrorCode errorCode;

	public OrganizationException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

}