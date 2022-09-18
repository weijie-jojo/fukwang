package com.fukwang.qo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminInfoQo implements Serializable {
    private String adminName;
    private String adminAccount;
    private Integer roleId;
    private String startTime;
    private String endTime;
    private Integer adminState;

}
