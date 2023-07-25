package homework1.exceptions;

public class PersonNotEntitledToDriveException extends RuntimeException {
    public PersonNotEntitledToDriveException() {
    }

    public PersonNotEntitledToDriveException(String message) {
        super(message);
    }
}
