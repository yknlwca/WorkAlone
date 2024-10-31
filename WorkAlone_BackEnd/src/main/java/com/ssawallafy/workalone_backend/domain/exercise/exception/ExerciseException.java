package com.ssawallafy.workalone_backend.domain.exercise.exception;

import lombok.Getter;

@Getter
public class ExerciseException extends RuntimeException{

	private final ErrorCode errorCode;

	public ExerciseException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

}