package com.ccsw.tutorial.loan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ClientMaxLoansException extends RuntimeException {
    public ClientMaxLoansException() {
        super("CLIENT_MAX_LOANS");
    }
}
