package com.fukwang.exception;

import lombok.Data;

@Data
public class GetException extends RuntimeException {
    public GetException(String message){
        super(message);
    }
}
