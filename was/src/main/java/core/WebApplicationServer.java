package core;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebApplicationServer {

    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {

                    // 요청으로부터 데이터 읽어들이는 부분
                    // TODO requestHandler 에게 요청 위임 후 자원 회수 로직 수정
                    InputStream is = socket.getInputStream();
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
                    OutputStream os = socket.getOutputStream();
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
                    System.err.println("클라이트 요청 수락 실패: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("클라이트 요청 수락 실패: " + e.getMessage());
        }
    }
}
