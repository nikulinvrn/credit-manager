package ru.trainee.creditmanager.exception;

public class CreditLimitExceedException extends RuntimeException{

    public CreditLimitExceedException() {
        super();
    }

    public CreditLimitExceedException(String message) {
        super(message);
    }
}
