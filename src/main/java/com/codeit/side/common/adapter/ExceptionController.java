package com.codeit.side.common.adapter;

import com.codeit.side.common.adapter.exception.*;
import com.codeit.side.common.adapter.response.ExceptionResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> businessExceptionHandler(HttpServletRequest request, BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();

        log.error("Request URL: {}, Error Message: {}", request.getRequestURL(), e.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(new ExceptionResponse(errorCode.getCode(), e.getMessage()));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> emailAlreadyExistsExceptionHandler(HttpServletRequest request, EmailAlreadyExistsException e) {
        ErrorCode errorCode = e.getErrorCode();

        log.error("Request URL: {}, Error Message: {}", request.getRequestURL(), e.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(new ExceptionResponse(errorCode.getCode(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;

        log.error("Request URL: {}, Error Message: {}", request.getRequestURL(), e.getMessage());

        String defaultMessage = e.getBindingResult().getAllErrors()
                .getFirst()
                .getDefaultMessage();

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(new ExceptionResponse(errorCode.getCode(), defaultMessage));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> userNotFoundExceptionHandler(HttpServletRequest request, UserNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();

        log.error("Request URL: {}, Error Message: {}", request.getRequestURL(), e.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(new ExceptionResponse(errorCode.getCode(), e.getMessage()));
    }

    @ExceptionHandler(IllegalRequestException.class)
    public ResponseEntity<ExceptionResponse> illegalRequestExceptionHandler(HttpServletRequest request, IllegalRequestException e) {
        ErrorCode errorCode = e.getErrorCode();

        log.error("Request URL: {}, Error Message: {}", request.getRequestURL(), e.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(new ExceptionResponse(errorCode.getCode(), e.getMessage()));
    }

    @ExceptionHandler(UserAlreadyJoinedException.class)
    public ResponseEntity<ExceptionResponse> userAlreadyJoinedExceptionHandler(HttpServletRequest request, UserAlreadyJoinedException e) {
        ErrorCode errorCode = e.getErrorCode();

        log.error("Request URL: {}, Error Message: {}", request.getRequestURL(), e.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(new ExceptionResponse(errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ExceptionResponse> jwtExceptionHandler(HttpServletRequest request, JwtException e) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        log.error("Request URL: {}, Error Message: {}", request.getRequestURL(), e.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(new ExceptionResponse(errorCode.getCode(), e.getMessage()));
    }

    @ExceptionHandler(ChatRoomNotFoundException.class)
    public ResponseEntity<ExceptionResponse> chatRoomNotFoundExceptionHandler(HttpServletRequest request, ChatRoomNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();

        log.error("Request URL: {}, Error Message: {}", request.getRequestURL(), e.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(new ExceptionResponse(errorCode.getCode(), e.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponse> missingServletRequestParameterExceptionHandler(HttpServletRequest request, MissingServletRequestParameterException e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;

        log.error("Request URL: {}, Error Message: {}", request.getRequestURL(), e.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(new ExceptionResponse(errorCode.getCode(), e.getMessage()));
    }
}
