package http.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeader {

    private final Map<String ,String> headers;

    private RequestHeader(Map<String, String> headers) {
        this.headers = Map.copyOf(headers);
    }

    public static RequestHeader from(List<String> headerList) {
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
}
