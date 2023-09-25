package za.co.Sky_Sage.Exceptions;

public class ConnectionException extends RuntimeException{
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
