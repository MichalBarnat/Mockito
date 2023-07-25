package homework1.exceptions;

public class PenaltyPointsHigherThan24Exception extends RuntimeException{
    public PenaltyPointsHigherThan24Exception() {
    }

    public PenaltyPointsHigherThan24Exception(String message) {
        super(message);
    }
}
