package com.ccsw.tutorial.loan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class GameUnavailableException extends RuntimeException {
    public GameUnavailableException() {
        super("GAME_UNAVAILABLE");
    }
}

