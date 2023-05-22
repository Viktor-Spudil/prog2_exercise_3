package at.ac.fhcampuswien.fhmdb.Exceptions;

import java.io.IOException;

public class MovieApiException extends IOException {

    public MovieApiException () {}

    public MovieApiException (String message) {
        super(message);
    }

    public MovieApiException (Throwable cause) {
        super(cause);
    }

    public MovieApiException (String message, Throwable cause) {
        super(message, cause);
    }
}
