package ru.sirius.natayarik.ft.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Yaroslav Ilin
 */

public class NotFoundDataException extends BaseApiException {
    public NotFoundDataException(String message) {
        super(message, "NOT_FOUND_DATA_ERROR", HttpStatus.BAD_REQUEST);
    }
}
