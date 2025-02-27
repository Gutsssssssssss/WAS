package core;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private final Socket connection;

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {

        // 요청으로부터 데이터 읽어들이는 부분
        // Request 에게 위임
        try (InputStream is = connection.getInputStream()) {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    break;
                }
                System.out.println(line);
            }

            // 응답으로 데이터를 보내는 부분
            // Response 에게 위임
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            String httpResponse =
                    "HTTP/1.1 200 OK\r\n" +          // HTTP 상태 코드
                            "Content-Type: text/plain\r\n" + // 헤더: Content-Type 설정
                            "Content-Length: 13\r\n" +       // 헤더: Content-Length 설정
                            "\r\n" +                         // 헤더와 바디를 구분하는 빈 줄
                            "Response Test";
            bw.write(httpResponse);
            bw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
