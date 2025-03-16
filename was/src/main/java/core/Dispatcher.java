package core;

import handler.Handler;
import handler.HandlerMatcher;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

public class Dispatcher implements Runnable {
    private static final Logger logger = LogManager.getLogger(Dispatcher.class);
    private final Socket connection;

    public Dispatcher(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        logger.info("Client connected");

        try (Socket socket = connection;
             InputStream is = socket.getInputStream();
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())
        ) {

            HttpRequest request = HttpRequest.from(is);
            HttpResponse response = new HttpResponse();
            Handler handler = HandlerMatcher.match(request.getPath());

            handler.handle(request, response);

            dos.writeBytes(String.format("%s %s %s\r%n", response.getStatusLine().getVersion(), response.getStatusLine().getHttpStatus().getCode(), response.getStatusLine().getHttpStatus().getMessage()));
            for (Map.Entry<String, String> entry : response.getHeader().getHeaders().entrySet()) {
                dos.writeBytes(String.format("%s: %s\r\n", entry.getKey(), entry.getValue()));
            }

            dos.writeBytes("\r\n");


            dos.write(response.getResponseBody());


            dos.flush();
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }

    }
}
