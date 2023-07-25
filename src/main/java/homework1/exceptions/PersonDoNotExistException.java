package homework1.exceptions;

public class PersonDoNotExistException extends RuntimeException {
    public PersonDoNotExistException() {
    }

    public PersonDoNotExistException(String message) {
        super(message);
    }
}
