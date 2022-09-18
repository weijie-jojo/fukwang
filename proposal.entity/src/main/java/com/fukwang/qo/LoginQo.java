package com.fukwang.qo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginQo implements Serializable {
    private String account;
    private String pwd;
    private String code;

}
