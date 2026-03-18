package com.ccsw.tutorial.loan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MoreThanTwoLoans extends RuntimeException {

    public MoreThanTwoLoans(String name) {
        super("El cliente con nombre '" + name + "' ya tiene 2 préstamos acttivos.");
    }

}
