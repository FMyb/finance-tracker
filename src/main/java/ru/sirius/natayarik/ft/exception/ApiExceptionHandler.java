package ru.sirius.natayarik.ft.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.sirius.natayarik.ft.data.ApiErrorResponse;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@ResponseBody
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundDataException.class, PermissionDeniedException.class})
    protected ResponseEntity<Object> handleError(BaseApiException ex, WebRequest request) {
        logger.error("Exception in occurred", ex);
        return handleExceptionInternal(
                ex,
                new ApiErrorResponse(ex.getErrorCode(), ex.getMessage()),
                new HttpHeaders(), ex.getHttpStatus(),
                request);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        List<ApiErrorResponse> errors = new ArrayList<>(ex.getFieldErrors().size());
        for (FieldError error : ex.getFieldErrors()) {
            errors.add(new ApiErrorResponse(
                    HttpStatus.BAD_REQUEST.name(),
                    String.format(
                            "Поле %s - %s",
                            error.getField(),
                            error.getDefaultMessage())));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}
