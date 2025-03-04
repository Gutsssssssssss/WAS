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
        HashMap<String, String> parsedHeader = new HashMap<>();

        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            int idx = line.indexOf(":");
            if (idx != -1) {
                String key = line.substring(0, idx).trim();
                String value = line.substring(idx + 1).trim();
                parsedHeader.put(key, value);
            }
        }

        return new RequestHeader(parsedHeader);
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
