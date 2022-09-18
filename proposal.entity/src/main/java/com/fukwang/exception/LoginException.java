package com.fukwang.exception;

import lombok.Data;

@Data
public class LoginException extends RuntimeException{
    public LoginException(String message) {
        super(message);
    }
}
