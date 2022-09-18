package com.fukwang.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fukwang.exception.DeleteException;
import com.fukwang.exception.InsertException;
import com.fukwang.exception.UpdateException;
import com.fukwang.mapper.TProposalMapper;
import com.fukwang.pojo.TProposal;
import com.fukwang.qo.TimeQo;
import com.fukwang.util.JudgeNull;
import com.fukwang.vo.TProposalVo;
import com.github.yulichang.base.mapper.MPJJoinMapper;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class TProposalServiceImpl implements TProposalService {

    @Resource
    private TProposalMapper tProposalMapper;
    /*
     * 多条件查询
     *
     * */
    @Override
    public IPage<TProposalVo> selectProposal(TProposal tProposal, TimeQo timeQo,Integer currentPage, Integer limit) {
        tProposal.setProposalPerson(JudgeNull.isNull(tProposal.getProposalPerson()));
        tProposal.setProposalCheck(JudgeNull.isNull(tProposal.getProposalCheck()));
        tProposal.setCompanyId(JudgeNull.isNull(tProposal.getCompanyId()));
        tProposal.setDepartmentId(JudgeNull.isNull(tProposal.getDepartmentId()));
        timeQo.setStartTime(JudgeNull.isNull(timeQo.getStartTime()));
        timeQo.setEndTime(JudgeNull.isNull(timeQo.getEndTime()));
        Integer currentPages=JudgeNull.isNull(currentPage);
        Integer limits=JudgeNull.isNull(limit);
        System.out.println("getProposalPerson=="+tProposal.getProposalPerson());
        System.out.println("getProposalCheck=="+tProposal.getProposalCheck());
        System.out.println("getCompanyId=="+tProposal.getCompanyId());
        System.out.println("getDepartmentId=="+tProposal.getDepartmentId());
        System.out.println("getStartTime=="+timeQo.getStartTime());
        System.out.println("getEndTime=="+timeQo.getEndTime());
        System.out.println("currentPages=="+currentPages);
        System.out.println("limits=="+limits);
        IPage<TProposalVo> tProposalVoIPage = tProposalMapper.selectJoinPage(new Page<>(currentPages,limits), TProposalVo.class,
                new MPJQueryWrapper<TProposal>()
                        .selectAll(TProposal.class)
                        .select("B.company_alias")
                        .select("C.department_name")
                        .innerJoin("t_company B on t.company_id=B.company_id")
                        .innerJoin("t_department C on t.department_id=C.department_id")
                        .like(tProposal.getProposalPerson()!=null,"t.proposal_person",tProposal.getProposalPerson())
                        .eq(tProposal.getProposalCheck()!=null,"t.proposal_check",tProposal.getProposalCheck())
                        .eq(tProposal.getCompanyId()!=null,"t.company_id",tProposal.getCompanyId())
                        .eq(tProposal.getDepartmentId()!=null,"t.department_id",tProposal.getDepartmentId())
                        .ge(timeQo.getStartTime()!=null,"t.gmt_create",timeQo.getStartTime())
                        .le(timeQo.getEndTime()!=null,"t.gmt_create",timeQo.getEndTime()));
        return tProposalVoIPage;
    }
    /*
     * 根据提案时间 据点id 分组查询各部门所有审核通过的单据数量
     *
     * */
    @Override
    public List<TProposalVo> proposalStatistic(Integer companyId, TimeQo timeQo) {
        timeQo.setStartTime(JudgeNull.isNull(timeQo.getStartTime()));
        timeQo.setEndTime(JudgeNull.isNull(timeQo.getEndTime()));
        Integer companyIds=JudgeNull.isNull(companyId);
        List<TProposalVo> tProposalVos = tProposalMapper.selectJoinList(TProposalVo.class,
                new MPJQueryWrapper<TProposal>()
                        .innerJoin("t_department B on t.department_id=B.department_id ")
                        .select("t.department_id")
                        .select("B.department_name")
                        .select("COUNT(1) count")
                        .eq(companyIds!=null,"t.company_id",companyId)
                        .eq("t.proposal_check",1)
                        .ge(timeQo.getStartTime()!=null,"t.gmt_create",timeQo.getStartTime())
                        .le(timeQo.getEndTime()!=null,"t.gmt_create",timeQo.getEndTime())
                        .groupBy("t.department_id")
                        .groupBy("B.department_name"));
        return tProposalVos;
    }
    /*
     * 查询所有数据
     *
     * */
    @Override
    public List<TProposalVo> selectAllProposal() {

        List<TProposalVo> tProposalVo = tProposalMapper.selectJoinList(TProposalVo.class,
                new MPJQueryWrapper<TProposal>()
                        .selectAll(TProposal.class)
                        .select("B.company_alias")
                        .select("C.department_name")
                        .select("D.state_name")
                        .innerJoin("t_company B on t.company_id=B.company_id")
                        .innerJoin("t_department C on t.department_id=C.department_id")
                        .innerJoin("t_state D on t.proposal_check=D.proposal_check"));
        return tProposalVo;
    }
    /*
     * 审核通过
     *
     * */
    @Override
    public Integer passCheck(Integer proposalId) {
        //更新的条件
        UpdateWrapper<TProposal> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("proposal_id",proposalId);
        updateWrapper.set("proposal_check",1);
        //执行更新操作
        int result = this.tProposalMapper.update(null, updateWrapper);
        if (result < 1) {
            throw new UpdateException("审核失败");
        }
        return result;
    }
    /*
     * 审核未通过
     *
     * */
    @Override
    public Integer failCheck(Integer proposalId) {
        //更新的条件
        UpdateWrapper<TProposal> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("proposal_id",proposalId);
        updateWrapper.set("proposal_check",2);
        //执行更新操作
        int result = this.tProposalMapper.update(null, updateWrapper);
        if (result < 1) {
            throw new UpdateException("审核失败");
        }
        return result;
    }
    /*
     * 删除成功
     *
     * */
    @Override
    public Integer delProposal(Integer proposalId) {
        //更新的条件
        QueryWrapper<TProposal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("proposal_id",proposalId);
        //执行更新操作
        int result = this.tProposalMapper.delete(queryWrapper);
        if (result < 1) {
            throw new DeleteException("审核失败");
        }
        return result;
    }
}
