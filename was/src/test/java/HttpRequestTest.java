import http.HttpMethod;
import http.HttpVersion;
import http.Path;
import http.HttpHeader;
import http.request.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("요청 startline 파싱 정상 동작 테스트")
    void testRequestStartLineParsing() throws IOException {
        String startLine = "GET /test.html HTTP/1.1";
        BufferedReader br = new BufferedReader(new StringReader(startLine));

        StartLine parsedStartLine = StartLine.from(br);

        assertEquals(HttpMethod.GET, parsedStartLine.getMethod());
        assertEquals("/test.html", parsedStartLine.getPath().getPath());
        assertEquals(HttpVersion.HTTP_1_1, parsedStartLine.getVersion());
    }

    @Test
    @DisplayName("요청 startline 파싱 실패 테스트 - 유효하지 않은 메서드")
    void testRequestStartLineWithWrongMethod() {
        String startLine = "OPTION /test.html HTTP/1.1";
        BufferedReader br = new BufferedReader(new StringReader(startLine));

        assertThrows(IllegalArgumentException.class, () -> StartLine.from(br));
    }

    @Test
    @DisplayName("요청 startline 파싱 실패 테스트 - 잘못된 startline 형식")
    void testRequestStartLineWithWrongForm() {
        String startLine = "OPTION /test.html";
        BufferedReader br = new BufferedReader(new StringReader(startLine));

        assertThrows(IllegalArgumentException.class, () -> StartLine.from(br));
    }

    @Test
    @DisplayName("요청 startline 파싱 실패 테스트 - HTTP 없는 버전")
    void testRequestStartLineWithInvalidVersion() {
        String startLine = "OPTION /test.html HTTP/1.2";
        BufferedReader br = new BufferedReader(new StringReader(startLine));

        assertThrows(IllegalArgumentException.class, () -> StartLine.from(br));
    }

    @Test
    @DisplayName("요청 헤더 파싱 테스트")
    void testRequestHeaderParsing() throws IOException {
        List<String> headerList = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "User-Agent: Mozilla/5.0"
        );
        String header =
                        "Host: localhost:8080\r\n" +
                        "Connection: keep-alive\r\n" +
                        "User-Agent: Mozilla/5.0\r\n";

        BufferedReader br = new BufferedReader(new StringReader(header));

        HttpHeader httpHeader = HttpHeader.from(br);
        Map<String, String> headers = httpHeader.getHeaders();
        assertEquals(3, headers.size());
        assertEquals("localhost:8080", headers.get("Host"));
        assertEquals("keep-alive", headers.get("Connection"));
        assertEquals("Mozilla/5.0", headers.get("User-Agent"));
    }

    @Test
    @DisplayName("요청 바디 파싱 테스트")
    void testRequestBodyParsing() throws IOException {
        String requestBody = "username=jm&password=123123";
        int contentLength = requestBody.getBytes(StandardCharsets.UTF_8).length;
        BufferedReader br = new BufferedReader(new StringReader(requestBody));

        RequestBody from = RequestBody.from(br, contentLength);
        assertEquals("jm", from.getBody().get("username"));
        assertEquals("123123", from.getBody().get("password"));
    }

    @Test
    @DisplayName("요청 바디 비었을 때 파싱 테스트")
    void testRequestBodyWithEmpty() throws IOException {
        String requestBody = "";
        int contentLength = requestBody.getBytes(StandardCharsets.UTF_8).length;
        BufferedReader br = new BufferedReader(new StringReader(requestBody));

        RequestBody from = RequestBody.from(br, contentLength);
        assertTrue(from.getBody().isEmpty());
    }

    @Test
    @DisplayName("최종 요청 객체 생성 테스트")
    void testHttpRequestInstance() throws IOException {
        String requestMessage =
                        "POST /login HTTP/1.1\r\n" +
                        "Host: localhost:8080\r\n" +
                        "Content-Type: application/x-www-form-urlencoded\r\n" +
                        "Content-Length: 34\r\n" +
                        "Connection: keep-alive\r\n" +
                        "\r\n" +
                        "username=jm&password=123123&rememberMe=true";
        InputStream is = new ByteArrayInputStream(requestMessage.getBytes(StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.from(is);

        assertEquals(HttpMethod.POST, request.getRequestStartLine().getMethod());
        assertEquals(34, request.getRequestHeader().getContentLength());
        assertEquals("jm", request.getRequestBody().getBody().get("username"));
    }

}
