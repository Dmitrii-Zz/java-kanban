package logic;

import java.io.IOException;

public class HttpServerException extends IOException {

    public HttpServerException(final String message) {
        super (message);
    }
}
