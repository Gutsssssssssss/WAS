package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    private static final String QUERY_STRING_PARTS_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    public static Map<String, String> parseQuery(String query) {
        Map<String, String> queryMap = new HashMap<>();
        String[] parts = query.split(QUERY_STRING_PARTS_DELIMITER);
        for (String part : parts) {
            String[] keyValue = part.split(KEY_VALUE_DELIMITER, 2);
            String key = keyValue[0];
            String value = keyValue.length > 1 ? keyValue[1] : "";
            queryMap.put(key, value);
        }
        return Map.copyOf(queryMap);
    }

    public static Map<String, String> parseHeader(BufferedReader br) throws IOException {
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
        return parsedHeader;
    }
}
