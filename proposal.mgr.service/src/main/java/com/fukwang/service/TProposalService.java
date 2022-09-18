package com.fukwang.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fukwang.pojo.TProposal;
import com.fukwang.qo.TimeQo;
import com.fukwang.vo.TProposalVo;

import java.util.List;

public interface TProposalService {
      IPage<TProposalVo> selectProposal(TProposal tProposal, TimeQo timeQo, Integer currentPage, Integer limit);
      Integer passCheck(Integer proposalId);
      Integer failCheck(Integer proposalId);
      Integer delProposal(Integer proposalId);
      List<TProposalVo> selectAllProposal();
      List<TProposalVo> proposalStatistic(Integer companyId, TimeQo timeQo);

}
