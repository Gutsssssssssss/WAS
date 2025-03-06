package http;

import http.request.RequestParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class HttpHeader {

    private final Map<String ,String> headers;

    private HttpHeader(Map<String, String> headers) {
        this.headers = Map.copyOf(headers);
    }

    public static HttpHeader from(BufferedReader br) throws IOException {
        return new HttpHeader(RequestParser.parseHeader(br));
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
