package handler;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Handler {

    void handle(HttpRequest request, HttpResponse response) throws URISyntaxException, IOException;
}
