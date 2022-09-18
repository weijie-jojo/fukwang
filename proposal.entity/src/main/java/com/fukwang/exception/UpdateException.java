package com.fukwang.exception;

import lombok.Data;

@Data
public class UpdateException extends RuntimeException{
    public UpdateException(String message){
        super(message);
    }
}
