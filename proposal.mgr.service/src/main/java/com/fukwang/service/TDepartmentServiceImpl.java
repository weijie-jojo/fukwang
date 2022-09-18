package com.fukwang.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fukwang.mapper.TDepartmentMapper;
import com.fukwang.pojo.TDepartment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class TDepartmentServiceImpl implements TDepartmentService {
    @Resource
    private TDepartmentMapper tDepartmentMapper;


    @Override
    public List<TDepartment> selectById(int companyId) {
        QueryWrapper<TDepartment> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("company_id",companyId);

        return tDepartmentMapper.selectList(queryWrapper);
    }
}
