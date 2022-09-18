package com.fukwang.exception;

import com.fukwang.dto.DataDto;
import com.fukwang.pojo.AdminInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public DataDto<AdminInfo> loginException(LoginException ex) {
        DataDto<AdminInfo> dto = new DataDto<>();
        return dto.err(ex.getMessage());
    }

    @ExceptionHandler(GetException.class)
    @ResponseBody
    public DataDto<Object> getException(GetException exception) {
        DataDto<Object> dto = new DataDto<>();
        return dto.err(exception.getMessage());
    }

    @ExceptionHandler(InsertException.class)
    @ResponseBody
    public DataDto<String> insertException(InsertException ex){
        DataDto dto = new DataDto();
        dto.err(ex.getMessage());
        return dto;
    }

    @ExceptionHandler(UpdateException.class)
    @ResponseBody
    public DataDto<String> updateException(UpdateException ex){
        DataDto dto = new DataDto();
        dto.err(ex.getMessage());
        return dto;
    }

    @ExceptionHandler(DeleteException.class)
    @ResponseBody
    public DataDto<String> deleteException(DeleteException ex){
        DataDto dto = new DataDto();
        dto.err(ex.getMessage());
        return dto;
    }
}
