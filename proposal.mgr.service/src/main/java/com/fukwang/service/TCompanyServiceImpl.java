package com.fukwang.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.fukwang.mapper.TCompanyMapper;
import com.fukwang.pojo.TCompany;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class TCompanyServiceImpl implements TCompanyService {
    @Resource
    private TCompanyMapper tCompanyMapper;

    @Override
    public List<TCompany> selectAll() {
        Wrapper<TCompany> queryWrapper=new QueryWrapper<>();
        return tCompanyMapper.selectList(queryWrapper);
    }
}
