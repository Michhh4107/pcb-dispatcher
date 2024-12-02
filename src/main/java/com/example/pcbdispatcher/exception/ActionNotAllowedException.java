package com.example.pcbdispatcher.exception;

public class ActionNotAllowedException extends RuntimeException {
    public ActionNotAllowedException(String message) {
        super(message);
    }
}
