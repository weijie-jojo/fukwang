package com.fukwang.vo;

import com.fukwang.pojo.TProposal;
import lombok.Data;

@Data
public class TProposalVo extends TProposal{
    private String departmentName;
    private String companyAlias;
    private String typeName;
    private String stateName;
    private Integer count;
}
