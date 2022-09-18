package com.fukwang.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class TProposal {
  private Integer proposalId;
  private String gmtCreate;
  private String proposalCode;
  private Integer proposalCheck;
  private Date gmtModified;
  private String proposalPerson;
  private Integer proposalPrize;
  private Integer companyId;
  private Integer departmentId;
  private String beforeImprovement;
  private String afterImprovement;
  private Integer saveMoney;
  private Integer saveTime;
  private String proposalName;
  private String proposalImage;
  private String proposalImage2;
}
