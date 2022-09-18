package com.fukwang.handler;


import com.fukwang.dto.DataDto;
import com.fukwang.pojo.AdminInfo;
import com.fukwang.pojo.TCompany;
import com.fukwang.pojo.TDepartment;
import com.fukwang.pojo.TProposal;

import com.fukwang.service.TCompanyService;
import com.fukwang.service.TDepartmentService;


import com.fukwang.service.TProposalService;
import com.fukwang.util.OrderNumGenerate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
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
     * 获取表单编号
     *
     * */
    @GetMapping("getProposalCode")
    public DataDto getProposalCode() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String nowDate = sdf.format(date);
        System.out.println("nowDate===="+nowDate);
        String proposalCode = "";

        for(TProposal tProposal:tProposalService.selectMaxCode()){
            System.out.println("tProposal.getProposalCode()===="+tProposal.getProposalCode());
            if(tProposal.getProposalCode().contains(nowDate)){

                Long orser=Long.valueOf(tProposal.getProposalCode().substring(2));
                orser++;
                System.out.println("orser===="+orser);
                proposalCode=String.valueOf("FK"+orser);
                System.out.println("proposalCode11===="+proposalCode);
            }else {
                proposalCode="FK"+nowDate+"001";
                System.out.println("proposalCode22===="+proposalCode);
            }
        }
        System.out.println("proposalCode===="+proposalCode);

//        TProposal  tProposal= tProposalService.queryProposal(proposalCode);
        DataDto dto=new DataDto();
//        dto.success(proposalCode);
//        if(tProposal!=null){
//            System.out.println("结算单编号重复");
//            getProposalCode();
//        }else {
//            dto.success(proposalCode);
//        }
        dto.success(proposalCode);
        return  dto;
    }
    /*
     * 插入表单数据
     *
     * */
    @PostMapping("addProposal")
    public DataDto addProposal(TProposal tProposal) {

        DataDto dto=new DataDto();
        //查重
        if( tProposalService.queryProposalByCode(tProposal.getProposalCode())==null){
            //插入数据
            tProposalService.addProposal(tProposal);
            dto.success("提案添加成功");
        }else {
            dto.success("提案编号重复");
        }
        //流出到客户端
        return dto;
    }
    /*
     *
     * 修改表单（插入图片）
     *
     * */
    @PostMapping("/updateImage")
    public DataDto updateImage(TProposal tProposal){
        DataDto dataDto=new DataDto();
        tProposalService.updateProposal(tProposal);
        dataDto.success("图片上传成功");
        return  dataDto;
    }
}
