package com.fukwang.pojo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AdminInfo implements Serializable {

  private Integer adminId;
  private String adminAccount;
  private String adminPwd;
  private String adminName;
  private Integer roleId;
  private String adminTel;
  private Integer adminState;
  private Date gmtCreate;
  private Date gmtModified;
  private String adminImage;


}
