package com.fukwang.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fukwang.exception.InsertException;
import com.fukwang.pojo.AdminLog;
import com.fukwang.qo.TimeQo;
import com.fukwang.vo.AdminLogVo;


public interface AdminLogService {
    Integer insertLog(AdminLog adminLog)  throws InsertException;
    IPage<AdminLogVo> selectAdminLog(String adminName, TimeQo timeQo, Integer currentPage, Integer limit);
}
