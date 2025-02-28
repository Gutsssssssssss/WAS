import http.request.RequestHeader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {


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
