package ee.taltech.iti0200.bonuscards.exceptions;

public class AlreadyExistingCardTypeException extends RuntimeException {
    String customMessage;

    public AlreadyExistingCardTypeException(String customMessage) {
        this.customMessage = customMessage;
    }

    @Override
    public String toString() {
        return customMessage;
    }
}
