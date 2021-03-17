package ee.taltech.iti0200.bonuscards.exceptions;

public class BonusException extends RuntimeException {
    String customMessage;

    public BonusException(String customMessage) {
        this.customMessage = customMessage;
    }

    @Override
    public String toString() {
        return customMessage;
    }

}

