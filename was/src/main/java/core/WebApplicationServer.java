package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebApplicationServer {
    private static final Logger logger = LogManager.getLogger(WebApplicationServer.class);

    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 10;


    public static void main(String[] args) {
        // TODO 추후 스레드 풀 타입 수정 고려
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("WebApplicationServer started port : {} ", PORT);

            while (true) {
                Socket connection = serverSocket.accept();
                executorService.execute(new Dispatcher(connection));
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
