package http.request;

import http.HttpVersion;
import http.Path;

import java.io.BufferedReader;
import java.io.IOException;

public class StartLine {

    private static final String START_LINE_DELIMITER = "\\s+";

    private final HttpMethod method;
    private final Path path;
    private final HttpVersion version;

    private StartLine(HttpMethod method, Path path, HttpVersion version) {
        this.method = method;
        this.path = path;
        this.version = version;
    }

    public static StartLine from(BufferedReader br) throws IOException {
        String startLine = br.readLine();
        if (startLine == null || startLine.isEmpty()) {
            throw new IllegalArgumentException("Start line cannot be null or empty");
        }

        String[] parts = startLine.split(START_LINE_DELIMITER);
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

        return new StartLine(method, path, version);
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
