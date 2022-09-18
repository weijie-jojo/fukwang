package com.fukwang.exception;

import lombok.Data;

@Data
public class DeleteException extends RuntimeException{
    public DeleteException(String message){
        super(message);
    }
}
