package com.fukwang.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fukwang.exception.InsertException;


import com.fukwang.exception.UpdateException;
import com.fukwang.mapper.TProposalMapper;
import com.fukwang.pojo.AdminInfo;
import com.fukwang.pojo.TProposal;

import com.fukwang.vo.TProposalVo;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class TProposalServiceImpl implements TProposalService {

    @Resource
    private TProposalMapper tProposalMapper;

    /*
     * 查询表单单编号是否重复
     *
     * */
    @Override
    public TProposal queryProposalByCode(String proposalCode){
        QueryWrapper<TProposal> queryWrapper=new QueryWrapper();
        queryWrapper.eq("proposal_code",proposalCode);
        return tProposalMapper.selectOne(queryWrapper);

    }
    /*
     * 查询数据库里面最后一条数据
     *
     * */
    @Override
    public List<TProposal> selectMaxCode(){
        IPage<TProposal> iPage=new Page<>(1,1);
        return tProposalMapper.selectPage(iPage,
                new QueryWrapper<TProposal>()
                .orderByDesc("proposal_id")).getRecords();
    }

    /*
    * 新增表单
    *
    * */
    @Override
    public Integer addProposal(TProposal tProposal) throws InsertException {
        tProposal.setProposalCheck(0);
        Integer rs=tProposalMapper.insert(tProposal);
        if (rs<1){
            throw  new InsertException("新增提案失败！");
        }
        return rs;
    }

    /*
     *
     * 修改表单（插入图片）
     *
     * */
    public  Integer updateProposal(TProposal tProposal){
        UpdateWrapper<TProposal> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("proposal_code",tProposal.getProposalCode());
        updateWrapper.set("proposal_image",tProposal.getProposalImage());
        updateWrapper.set("proposal_image2",tProposal.getProposalImage2());
        Integer count=tProposalMapper.update(null,updateWrapper);
        if (count<0){
            throw  new UpdateException("上传失败！");
        }
        return count;
    }
}
