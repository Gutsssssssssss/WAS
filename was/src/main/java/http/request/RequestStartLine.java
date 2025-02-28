package http.request;

import http.HttpMethod;

public class RequestStartLine {

    private final HttpMethod method;
    private final Path path;
    private final HttpVersion version;

    private RequestStartLine(HttpMethod method, Path path, HttpVersion version) {
        this.method = method;
        this.path = path;
        this.version = version;
    }

    public static RequestStartLine from(String startLine) {
        if (startLine == null || startLine.isEmpty()) {
            throw new IllegalArgumentException("Start line cannot be null or empty");
        }

        String[] parts = startLine.split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid HTTP start line format: " + startLine);
        }

        HttpMethod method;
        try {
            method = HttpMethod.valueOf(parts[0]);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid HTTP method: " + parts[0]);
        }

        Path path = Path.from(parts[1]);

        HttpVersion version = HttpVersion.from(parts[2]);

        return new RequestStartLine(method, path, version);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Path getPath() {
        return path;
    }

    public HttpVersion getVersion() {
        return version;
    }
}
