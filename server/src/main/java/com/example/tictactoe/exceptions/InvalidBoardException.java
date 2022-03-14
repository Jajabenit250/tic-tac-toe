package com.example.tictactoe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBoardException extends Exception {
    public InvalidBoardException(String message) {
        super(message);
    }
}
