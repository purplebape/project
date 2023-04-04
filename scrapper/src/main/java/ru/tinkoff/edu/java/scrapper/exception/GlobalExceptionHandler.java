package ru.tinkoff.edu.java.scrapper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import ru.tinkoff.edu.java.scrapper.model.controller.ApiErrorResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice(
        basePackages = "ru.tinkoff.edu.java.scrapper.controller"
)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String ERROR_MESSAGE = "Error handler is not implemented yet";
    private static final String ERROR_CODE = "400";

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        List<String> stackTrace = Arrays.stream(ex.getStackTrace()).map(Objects::toString).collect(Collectors.toList());
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                ERROR_MESSAGE,
                ERROR_CODE,
                ex.toString(),
                ex.getMessage(),
                stackTrace);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
