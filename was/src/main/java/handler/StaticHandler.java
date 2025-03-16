package handler;

import http.HttpHeader;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.ResponseBody;
import http.response.StatusLine;


import java.io.IOException;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticHandler implements Handler {


    @Override
    public void handle(HttpRequest request, HttpResponse response) throws URISyntaxException, IOException {
        InputStream is = StaticHandler.class.getClassLoader().getResourceAsStream("static" + request.getPath());
        byte[] bytes = is.readAllBytes();
        StatusLine statusLine = StatusLine.from(request.getRequestStartLine().getVersion(), HttpStatus.OK);
        String contentType = "image/png";
        HttpHeader headers = HttpHeader.createResponseHeaders(contentType, bytes.length);
        ResponseBody responseBody = new ResponseBody(bytes);
        response.setStatusLine(statusLine);
        response.setHeader(headers);
        response.setResponseBody(responseBody);

    }
}
