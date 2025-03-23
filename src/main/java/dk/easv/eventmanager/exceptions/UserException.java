package dk.easv.eventmanager.exceptions;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
    public UserException(Exception e) { super(e); }
}
