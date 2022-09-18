package com.fukwang.vo;

import com.fukwang.pojo.AdminInfo;
import lombok.Data;
import java.util.Date;


@Data
public class AdminRoleVo extends AdminInfo {
  private Integer roleId;
  private String roleName;
  private Integer roleState;
  private Date gmtCreate;
}
