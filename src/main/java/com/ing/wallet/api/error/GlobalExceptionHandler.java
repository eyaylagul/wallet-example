package com.ing.wallet.api.error;

import com.ing.wallet.domain.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomerNotFoundException.class, WalletNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(RuntimeException ex, HttpServletRequest req) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI());
    }


    @ExceptionHandler({InsufficientBalanceException.class, WithdrawNotAllowedException.class, InvalidOperationException.class})
    public ResponseEntity<ErrorResponse> handleInsufficientExceptions(RuntimeException ex, HttpServletRequest req) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {

        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .reduce((m1, m2) -> m1 + "; " + m2).orElse("Validation error");
        return buildResponse(HttpStatus.BAD_REQUEST, msg, req.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(Exception ex, HttpServletRequest req) {
        ex.printStackTrace();
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected Error :/", req.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, String path) {
        ErrorResponse body = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                path,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(body, status);
    }
}