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
        List<String> headerList = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            headerList.add(line);
        }

        HashMap<String, String> parsedHeader = new HashMap<>();
        for (String s : headerList) {
            int idx = s.indexOf(":");
            if (idx != -1) {
                String key = s.substring(0, idx).trim();
                String value = s.substring(idx + 1).trim();
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
