package com.qa.api.constants;

public enum APIHttpStatus {
    OK_200(200, "OK"),
    CREATED_201(201, "Created"),
    NO_CONTENT_204(204, "No Content"),
    BAD_REQUEST_400(400, "Bad Request"),
    UNAUTHORIZED_401(401, "Unauthorized"),
    FORBIDDEN_403(403, "Forbidden"),
    NOT_FOUND_404(404, "Not Found"),
    INTERNAL_SERVER_ERROR_500(500, "Internal Server Error");

    private final int code;
    private final String message;

    APIHttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return code + " " + message;
    }
}
