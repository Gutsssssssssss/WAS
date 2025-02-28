import http.request.Path;
import http.request.RequestHeader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpRequestTest {

    @Test
    @DisplayName("요청 startline path 만 있을 때 테스트")
    void testRequestStartLineOnlyPathParsing() {
        String onlyPath = "/user";

        Path path = Path.from(onlyPath);

        assertEquals("/user", path.getPath());
        assertTrue(path.getQuery().isEmpty());
    }

    @Test
    @DisplayName("요청 startline path 와 query 함께 있을 때 테스트")
    void testRequestStartLinePathWithQueryParsing() {
        String pathWithQuery = "/user?id=jm&password=123";

        Path path = Path.from(pathWithQuery);

        assertEquals("/user", path.getPath());
        assertEquals("jm", path.getQuery().get("id"));
        assertEquals("123", path.getQuery().get("password"));
    }


    @Test
    @DisplayName("요청 헤더 파싱 테스트")
    void testRequestHeaderParsing() {
        List<String> headerList = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "User-Agent: Mozilla/5.0"
        );

        RequestHeader requestHeader = RequestHeader.from(headerList);
        Map<String, String> headers = requestHeader.getHeaders();
        assertEquals(3, headers.size());
        assertEquals("localhost:8080", headers.get("Host"));
        assertEquals("keep-alive", headers.get("Connection"));
        assertEquals("Mozilla/5.0", headers.get("User-Agent"));
    }
}
