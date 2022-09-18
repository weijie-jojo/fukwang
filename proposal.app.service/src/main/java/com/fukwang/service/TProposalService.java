package com.fukwang.service;



import com.fukwang.pojo.TProposal;

import java.util.List;


public interface TProposalService {
     Integer addProposal(TProposal tProposal);
     TProposal queryProposalByCode(String proposalCode);
     List<TProposal> selectMaxCode();
     Integer updateProposal(TProposal tProposal);
}
