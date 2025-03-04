package http.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Path {

    private static final String QUERY_STRING_DELIMITER = "?";
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
            query = RequestParser.parseQuery(fullPath.substring(queryIndex + 1));
        } else {
            path = fullPath;
            query = Collections.emptyMap();
        }
        return new Path(path, query);
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQuery() {
        return query;
    }
}
