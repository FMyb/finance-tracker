package ru.sirius.natayarik.ft.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Yaroslav Ilin
 */

public class PermissionDeniedException extends BaseApiException {
    public PermissionDeniedException(String message) {
        super(message, "PERMISSION_DENIED_ERROR", HttpStatus.FORBIDDEN);
    }
}
