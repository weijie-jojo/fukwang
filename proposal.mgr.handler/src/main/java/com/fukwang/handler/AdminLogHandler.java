package com.fukwang.handler;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fukwang.dto.DataDto;
import com.fukwang.pojo.AdminLog;
import com.fukwang.pojo.TDict;
import com.fukwang.qo.TimeQo;
import com.fukwang.service.AdminLogService;
import com.fukwang.service.TRoleService;
import com.fukwang.vo.AdminLogVo;
import com.fukwang.vo.AdminRoleVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class AdminLogHandler {
    @Resource
    private AdminLogService adminLogService;
    /*
     * 条件查询日志
     *
     * */
    @GetMapping("selectAdminLog")
    public DataDto selectAdminLog(String adminName, TimeQo timeQo, Integer currentPage, Integer limit){
        IPage<AdminLogVo> adminLogVoIPage=adminLogService.selectAdminLog(adminName,timeQo,currentPage,limit);
        DataDto<AdminLogVo> dataDto = new DataDto<>();
        dataDto.success(adminLogVoIPage.getRecords(),adminLogVoIPage.getTotal());
        return dataDto;
    }
}
