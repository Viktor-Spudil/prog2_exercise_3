package Exceptions;

public class MovieApiException extends RuntimeException{

    public MovieApiException (String message) {
        super(message);
    }

    public MovieApiException (String message, Throwable cause) {
        super(message, cause);
    }
}
