package com.helpshift.exceptions;

/**
 * Created by amitsingh.c on 06/01/17.
 */
public class DuplicateContactException extends RuntimeException {
    public DuplicateContactException(String msg) {
        super(msg);
    }
}
