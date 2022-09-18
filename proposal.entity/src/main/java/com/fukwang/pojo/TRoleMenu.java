package com.fukwang.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TRoleMenu implements Serializable {

  private Integer rmId;
  private Integer roleId;
  private Integer menuId;

}
