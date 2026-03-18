package com.ccsw.tutorial.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // 409
public class ClientAlreadyExistsException extends RuntimeException {
    public ClientAlreadyExistsException(String name) {
        super("El cliente con nombre '" + name + "' ya existe");
    }
}