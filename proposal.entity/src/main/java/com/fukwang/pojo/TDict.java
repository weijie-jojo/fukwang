package com.fukwang.pojo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TDict implements Serializable {

  private Integer dictId;
  private String typeName;
  private Integer typeId;
  private String typeAlias;
  private Integer typeState;
  private Date gmtCreate;
  private Date gmtModified;



}
