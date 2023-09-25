package za.co.Sky_Sage.Exceptions;

public class ApiResponseException extends RuntimeException {
    public ApiResponseException(String message) {
        super(message);
    }
}
