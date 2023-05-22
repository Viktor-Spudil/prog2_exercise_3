package at.ac.fhcampuswien.fhmdb.Exceptions;

public class MovieApiException extends RuntimeException {

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
