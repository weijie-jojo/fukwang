package com.fukwang.exception;

import lombok.Data;

@Data
public class InsertException extends RuntimeException {
    public InsertException(String message) {
        super(message);
    }
}
