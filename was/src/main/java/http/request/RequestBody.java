package http.request;

import javax.xml.validation.Validator;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {

    private Map<String, String> body;

    private RequestBody(Map<String, String> body) {
        this.body = Map.copyOf(body);
    }

    public static RequestBody from(BufferedReader br, int contentLength) throws IOException {
        if (contentLength > 0) {
            char[] buffer = new char[contentLength];
            br.read(buffer, 0, contentLength);

            String requestBody = new String(buffer).trim();
            Map<String, String> parsedBody = parseQueryString(requestBody);

            return new RequestBody(parsedBody);
        }
        return empty();
    }

    private static Map<String, String> parseQueryString(String queryString) {
        if (queryString.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, String> result = new HashMap<>();
        String[] pairs = queryString.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            String key = keyValue[0];
            String value = keyValue.length > 1 ? keyValue[1] : "";
            result.put(key, value);
        }

        return result;
    }

    private static RequestBody empty() {
        return new RequestBody(Collections.emptyMap());
    }

    public Map<String, String> getBody() {
        return body;
    }
}
