package homework1.exceptions;

public class CarDoNotExistException extends RuntimeException{
    public CarDoNotExistException() {
    }

    public CarDoNotExistException(String message) {
        super(message);
    }
}
