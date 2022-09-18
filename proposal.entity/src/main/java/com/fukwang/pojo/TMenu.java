package com.fukwang.pojo;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;


@Data
public class TMenu implements Serializable {

  private Integer menuId;
  private String menuName;
  private String menuUrl;
  private Integer menuState;
  private Integer menuPid;
  private String menuIcon;
  private Date gmtCreate;
  private Date gmtModified;
}
