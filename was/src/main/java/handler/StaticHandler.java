package handler;

import http.HttpHeader;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.ResponseBody;
import http.response.StatusLine;


import java.io.IOException;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticHandler implements Handler {


    @Override
    public void handle(HttpRequest request, HttpResponse response) throws URISyntaxException, IOException {
        Path path = Paths.get(StaticHandler.class.getClassLoader().getResource("static" + request.getPath()).toURI());
        byte[] bytes = Files.readAllBytes(path);
        StatusLine statusLine = StatusLine.from(request.getRequestStartLine().getVersion(), HttpStatus.OK);
        String contentType = Files.probeContentType(path);
        HttpHeader headers = HttpHeader.createResponseHeaders(contentType, bytes.length);
        ResponseBody responseBody = new ResponseBody(bytes);
        response.setStatusLine(statusLine);
        response.setHeader(headers);
        response.setResponseBody(responseBody);

    }
}
