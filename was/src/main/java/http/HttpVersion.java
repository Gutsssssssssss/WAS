package http;

public enum HttpVersion {

    HTTP_1_1("HTTP/1.1");

    private final String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public static HttpVersion from(String version) {
        for (HttpVersion v : values()) {
            if (v.version.equals(version)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Unsupported HTTP version: " + version);
    }
}
