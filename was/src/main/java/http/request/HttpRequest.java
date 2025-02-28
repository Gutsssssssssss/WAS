package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpRequest {

    private RequestStartLine requestStartLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    public HttpRequest() {
    }

    public HttpRequest(RequestStartLine requestStartLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestStartLine = requestStartLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest from(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String startLine = br.readLine();

        List<String> headerList = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            headerList.add(line);
        }
        RequestHeader header = RequestHeader.from(headerList);

        return new HttpRequest();
    }

}
