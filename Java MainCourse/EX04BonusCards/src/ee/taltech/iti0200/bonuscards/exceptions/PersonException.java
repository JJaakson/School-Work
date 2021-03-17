package ee.taltech.iti0200.bonuscards.exceptions;

public class PersonException extends RuntimeException {
    String customMessage;

    public PersonException(String customMessage) {
        this.customMessage = customMessage;
    }

    @Override
    public String toString() {
        return customMessage;
    }

}
