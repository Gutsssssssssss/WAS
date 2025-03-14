package http.response;

import http.HttpVersion;

public class StatusLine {

    private final HttpVersion version;
    private final HttpStatus httpStatus;

    private StatusLine(HttpVersion version, HttpStatus httpStatus) {
        this.version = version;
        this.httpStatus = httpStatus;
    }

    public static StatusLine from(HttpVersion version, HttpStatus httpStatus) {
        return new StatusLine(version, httpStatus);
    }

    public HttpVersion getVersion() {
        return version;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
