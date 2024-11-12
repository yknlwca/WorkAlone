package com.ssawallafy.workalone_backend.domain.exercise.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EXERCISE_TYPE_NOT_CORRECT(HttpStatus.BAD_REQUEST, "운동 타입이 잘못되었습니다."),
    EXERCISE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 운동입니다."),
    EMPTY_SET_TYPE(HttpStatus.BAD_REQUEST, "세트 타입이 비어있습니다."),
    INVALID_SET_TYPE(HttpStatus.BAD_REQUEST, "세트 타입이 잘못되었습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
