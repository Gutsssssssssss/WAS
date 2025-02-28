package http.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Path {

    private final String path;
    private final Map<String, String> query;

    private Path(String path, Map<String, String> query) {
        this.path = path;
        this.query = query;
    }

    public static Path from(String fullPath) {
        int queryIndex = fullPath.indexOf("?");

        String path;
        Map<String, String> query;
        if (queryIndex != -1) {
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
        String[] parts = query.split("&");
        for (String part : parts) {
            String[] keyValue = part.split("=");
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
