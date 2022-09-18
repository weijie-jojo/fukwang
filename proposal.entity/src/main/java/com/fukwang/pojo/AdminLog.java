package com.fukwang.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AdminLog implements Serializable {

  private Integer logId;
  private String logUrl;
  private Integer adminId;
  private Date gmtCreate;


}
