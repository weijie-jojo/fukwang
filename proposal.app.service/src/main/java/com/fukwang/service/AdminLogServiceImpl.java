package com.fukwang.service;

import com.fukwang.exception.InsertException;
import com.fukwang.mapper.AdminLogMapper;
import com.fukwang.pojo.AdminLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class AdminLogServiceImpl implements AdminLogService {
    @Resource
    private AdminLogMapper adminLogMapper;


    @Override
    public Integer insertLog(AdminLog adminLog) throws InsertException {

        Integer rs = adminLogMapper.insert(adminLog);
        if (rs < 1) {
            throw new InsertException("插入日志失败，请联系管理员");
        }
        return rs;
    }
}
