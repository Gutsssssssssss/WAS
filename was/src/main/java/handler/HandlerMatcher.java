package handler;

public class HandlerMatcher {

    private static final Handler staticHandler = new StaticHandler();

    public static Handler match(String path) {
        return staticHandler;
    }
}
