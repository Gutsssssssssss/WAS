package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class RequestHeader {

    private final Map<String ,String> headers;

    private RequestHeader(Map<String, String> headers) {
        this.headers = Map.copyOf(headers);
    }

    public static RequestHeader from(BufferedReader br) throws IOException {
        return new RequestHeader(RequestParser.parseHeader(br));
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getContentLength() {
        String contentLength = headers.get("Content-Length");
        if (contentLength == null) {
            return 0;
        }
        return Integer.parseInt(contentLength);
    }
}
