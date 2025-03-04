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
        if (contentLength == 0) {
            return empty();
        }

        char[] buffer = new char[contentLength];
        br.read(buffer, 0, contentLength);

        String requestBody = new String(buffer).trim();
        Map<String, String> parsedBody = RequestParser.parseQuery(requestBody);

        return new RequestBody(parsedBody);
    }


    private static RequestBody empty() {
        return new RequestBody(Collections.emptyMap());
    }

    public Map<String, String> getBody() {
        return body;
    }
}
