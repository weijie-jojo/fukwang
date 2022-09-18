package com.fukwang.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TRole implements Serializable {

  private Integer roleId;
  private String roleName;
  private Integer roleState;
  private Date gmtCreate;

}
