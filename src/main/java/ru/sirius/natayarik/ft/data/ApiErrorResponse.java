package ru.sirius.natayarik.ft.data;

/**
 * @author Yaroslav Ilin
 */

public class ApiErrorResponse {
    private String errorCode;
    private String errorMessage;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
