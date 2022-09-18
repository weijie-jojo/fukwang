package com.fukwang.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fukwang.mapper.TDictMapper;
import com.fukwang.pojo.TDict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TDictServiceImpl implements TDictService{
    @Resource
    private TDictMapper tDictMapper;
    @Override
    public List<TDict> selectAll() {
        QueryWrapper<TDict> queryWrapper=new QueryWrapper();
        return tDictMapper.selectList(queryWrapper);

    }
}
