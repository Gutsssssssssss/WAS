package http.request;

import http.HttpHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {

    private StartLine startLine;
    private HttpHeader httpHeader;
    private RequestBody requestBody;


    private HttpRequest(StartLine startLine, HttpHeader httpHeader, RequestBody requestBody) {
        this.startLine = startLine;
        this.httpHeader = httpHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest from(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        StartLine parsedStartLine = StartLine.from(br);

        HttpHeader parsedHeader = HttpHeader.from(br);

        int contentLength = parsedHeader.getContentLength();
        RequestBody paresedRequestBody = RequestBody.from(br, contentLength);

        return new HttpRequest(parsedStartLine, parsedHeader, paresedRequestBody);
    }

    public StartLine getRequestStartLine() {
        return startLine;
    }

    public HttpHeader getRequestHeader() {
        return httpHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}
