package http.response;

import http.HttpHeader;

public class HttpResponse {

    private StatusLine statusLine;
    private HttpHeader header;
    private ResponseBody responseBody;

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public HttpHeader getHeader() {
        return header;
    }

    public byte[] getResponseBody() {
        return responseBody.getBody();
    }

    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public void setHeader(HttpHeader header) {
        this.header = header;
    }

    public void setResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }
}
