package com.ssawallafy.workalone_backend.domain.organization.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ORGANIZATION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 모임입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
