package com.fukwang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fukwang.mapper.AdminLogMapper;
import com.fukwang.exception.InsertException;
import com.fukwang.pojo.AdminInfo;
import com.fukwang.pojo.AdminLog;
import com.fukwang.qo.TimeQo;
import com.fukwang.util.JudgeNull;
import com.fukwang.vo.AdminLogVo;
import com.fukwang.vo.AdminRoleVo;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class AdminLogServiceImpl implements AdminLogService {
    @Resource
    private AdminLogMapper adminLogMapper;

    /*
    * 插入日志
    * */
    @Override
    public Integer insertLog(AdminLog adminLog) throws InsertException {

        Integer rs = adminLogMapper.insert(adminLog);
        if (rs < 1) {
            throw new InsertException("插入日志失败，请联系管理员");
        }
        return rs;
    }
    /*
     * 条件查询日志
     * */
    @Override
    public IPage<AdminLogVo> selectAdminLog(String adminName, TimeQo timeQo, Integer currentPage, Integer limit) {
        timeQo.setStartTime(JudgeNull.isNull(timeQo.getStartTime()));
        timeQo.setEndTime(JudgeNull.isNull(timeQo.getEndTime()));
        String adminNames=JudgeNull.isNull(adminName);
        Integer currentPages=JudgeNull.isNull(currentPage);
        Integer limits=JudgeNull.isNull(limit);
        System.out.println("adminNames=="+adminNames);
        System.out.println("getStartTime=="+timeQo.getStartTime());
        System.out.println("getEndTime=="+timeQo.getEndTime());
        System.out.println("currentPages=="+currentPages);
        System.out.println("limits=="+limits);
        IPage<AdminLogVo> adminInfoIPage = adminLogMapper.selectJoinPage(new Page<>(currentPages,limits), AdminLogVo.class,
                new MPJQueryWrapper<AdminLog>()
                        .selectAll(AdminLog.class)
                        .select("B.admin_name")
                        .innerJoin("admin_info B on t.admin_id=B.admin_id")
                        .like(adminNames!=null,"B.admin_name",adminNames)
                        .ge(timeQo.getStartTime()!=null,"t.gmt_create",timeQo.getStartTime())
                        .le(timeQo.getEndTime()!=null,"t.gmt_create",timeQo.getEndTime()));
        return adminInfoIPage;
    }
}
