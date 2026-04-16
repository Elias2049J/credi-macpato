package com.runaitec.credimacpato.config;

import com.runaitec.credimacpato.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                request.getMethod(),
                request.getRequestURI(),
                "Unauthorized:"+ ex.getMessage(),
                LocalDateTime.now()
        );
        log.warn("[handleAuthentication] {} {}", request.getMethod(), request.getRequestURI());
        return ResponseEntity.status(401).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        log.error("[handleIllegalArgument] Exception at path: {}, method: {}, error: {}", request.getRequestURI(), request.getMethod(), error, ex);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointer(NullPointerException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                request.getMethod(),
                request.getRequestURI(),
                "Dato nulo encontrado",
                LocalDateTime.now()
        );
        log.error("[handleNullPointer] Exception at path: {}, method: {}, error: {}", request.getRequestURI(), request.getMethod(), error, ex);
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                request.getMethod(),
                request.getRequestURI(),
                "Unexpected error: " + e.getMessage(),
                LocalDateTime.now()
        );
        log.error("[handleAllExceptions] Exception at path: {}, method: {}, error: {}", request.getRequestURI(), request.getMethod(), error, e);
        return ResponseEntity.internalServerError().body(error);
    }
}