package com.fukwang.handler;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fukwang.dto.DataDto;
import com.fukwang.pojo.TCompany;
import com.fukwang.pojo.TDepartment;
import com.fukwang.pojo.TProposal;
import com.fukwang.qo.LoginQo;
import com.fukwang.qo.TimeQo;
import com.fukwang.service.TCompanyService;
import com.fukwang.service.TDepartmentService;
import com.fukwang.service.TProposalService;
import com.fukwang.vo.TProposalVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class TProposalHandler {
    @Resource
    private TProposalService tProposalService;
    @Resource
    private TCompanyService tCompanyService;
    @Resource
    private TDepartmentService tDepartmentService;

    @GetMapping("/getAllCompany")
    public DataDto getAllCompany(){
        List<TCompany> companyList=tCompanyService.selectAll();
        DataDto<TCompany> tCompanyDataDto = new DataDto<>();
        return tCompanyDataDto.success(companyList);
    }


    @GetMapping("/selectDepartmentByCompanyId")
    public DataDto selectDepartmentByCompanyId(int companyId){
        List<TDepartment> departmentList=tDepartmentService.selectById(companyId);
        DataDto<TDepartment> tCompanyDataDto = new DataDto<>();
        return tCompanyDataDto.success(departmentList);
    }

    /*
     * 多条件查询提案表信息
     *
     * */
    @GetMapping("selectProposal")
    public DataDto selectProposal(TProposal tProposal, TimeQo timeQo, HttpServletRequest req){
        //从前端获取数据
        Integer currentPage=Integer.parseInt(req.getParameter("currentPage"));
        Integer limit=Integer.parseInt(req.getParameter("limit"));
        System.out.println("getProposalCheck=="+tProposal.getProposalCheck());
        System.out.println("getCTime22=="+timeQo.getStartTime());
        System.out.println("getEndTime22=="+timeQo.getEndTime());
        //获取所有结算表信息
        IPage<TProposalVo> tProposalVoIPage=tProposalService.selectProposal(tProposal,timeQo,currentPage,limit);
        DataDto dto=new DataDto();
        dto.success(tProposalVoIPage.getRecords(),tProposalVoIPage.getTotal());
        //流出到客户端
        return dto;
    }
    /*
     * 根据提案时间 据点id 查询各部门所有审核通过的单据数量（统计）
     *
     * */
    @GetMapping("proposalStatistic")
    public DataDto proposalStatistic(HttpServletRequest req, TimeQo timeQo){
        Integer companyId=Integer.parseInt(req.getParameter("companyId"));
        List<TProposalVo> tProposalVos=tProposalService.proposalStatistic(companyId,timeQo);
        DataDto dto =  new DataDto();
        dto.success(tProposalVos);
        return  dto;
    }
    /*
     * 查询所有数据
     *
     * */
    @GetMapping("selectExcelData")
    public DataDto selectAllProposal(TProposal tProposal, TimeQo timeQo, HttpServletRequest req){
        //获取所有结算表信息
        List<TProposalVo> tProposalVo=tProposalService.selectAllProposal();
        DataDto dto=new DataDto();
        dto.success(tProposalVo);
        //流出到客户端
        return dto;
    }
    /*
     * 审核通过
     *
     * */
    @PostMapping("/passCheck")
    public DataDto passCheck(HttpServletRequest req){
        Integer proposalId=Integer.parseInt(req.getParameter("proposalId"));
        tProposalService.passCheck(proposalId);
        DataDto dto = new DataDto();
        dto.success("审核通过!");
        //流出到客户端
        return dto;
    }
    /*
     * 审核未通过
     *
     * */
    @PostMapping("/failCheck")
    public DataDto failCheck(HttpServletRequest req){
        Integer proposalId=Integer.parseInt(req.getParameter("proposalId"));
        tProposalService.failCheck(proposalId);
        DataDto dto = new DataDto();
        dto.success("审核未通过!");
        //流出到客户端
        return dto;
    }
    /*
     * 审核未通过
     *
     * */
    @PostMapping("/delProposal")
    public DataDto delProposal(HttpServletRequest req){
        Integer proposalId=Integer.parseInt(req.getParameter("proposalId"));
        tProposalService.delProposal(proposalId);
        DataDto dto = new DataDto();
        dto.success("删除成功!");
        //流出到客户端
        return dto;
    }
}
