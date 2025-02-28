package http.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Path {

    private static final String QUERY_STRING_DELIMITER = "?";
    private static final String QUERY_STRING_PARTS_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int QUERY_STRING_NOT_FOUND = -1;


    private final String path;
    private final Map<String, String> query;

    private Path(String path, Map<String, String> query) {
        this.path = path;
        this.query = query;
    }

    public static Path from(String fullPath) {
        int queryIndex = fullPath.indexOf(QUERY_STRING_DELIMITER);

        String path;
        Map<String, String> query;
        if (queryIndex != QUERY_STRING_NOT_FOUND) {
            path = fullPath.substring(0, queryIndex);
            query = parseQuery(fullPath.substring(queryIndex + 1));
        } else {
            path = fullPath;
            query = Collections.emptyMap();
        }
        return new Path(path, query);
    }

    private static Map<String, String> parseQuery(String query) {
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

    public String getPath() {
        return path;
    }

    public Map<String, String> getQuery() {
        return query;
    }
}
