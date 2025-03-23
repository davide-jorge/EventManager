package dk.easv.eventmanager.exceptions;

public class EventException extends RuntimeException {
    public EventException(String message) {
        super(message);
    }
    public EventException(Exception e) { super(e); }
}
