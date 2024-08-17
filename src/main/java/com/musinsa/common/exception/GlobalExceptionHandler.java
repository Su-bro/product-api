package com.musinsa.common.exception;

import com.musinsa.common.response.ErrorBody;
import com.musinsa.common.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorBody> handleException(Exception e) {
        LOGGER.error("Unexpected error occurred", e);
        return ResponseEntity.internalServerError().body(new ErrorBody(MessageUtil.getMsg("E001")));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorBody> handleCategoryNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.badRequest().body(new ErrorBody(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorBody> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(new ErrorBody(e.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage).toList()));
    }

}
