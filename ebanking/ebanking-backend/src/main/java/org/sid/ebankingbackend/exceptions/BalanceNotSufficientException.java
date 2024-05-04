package org.sid.ebankingbackend.exceptions;

public class BalanceNotSufficientException extends Exception {
    public BalanceNotSufficientException(String msg) {
        super(msg);

    }
}
