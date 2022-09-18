package com.fukwang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fukwang.exception.DeleteException;
import com.fukwang.exception.InsertException;
import com.fukwang.exception.LoginException;
import com.fukwang.exception.UpdateException;
import com.fukwang.pojo.AdminInfo;
import com.fukwang.qo.LoginQo;
import com.fukwang.qo.TimeQo;
import com.fukwang.vo.AdminRoleVo;

import java.util.List;


public interface AdminInfoService {
    AdminInfo login(LoginQo qo, String code) throws LoginException;
    List<AdminRoleVo> selectAdminRoleById(Integer adminId);
    Integer updateAdmin(String pass,Integer adminId ,Integer typeId)throws UpdateException;
    Integer updateAdminInfo(AdminInfo adminInfo);
//    Integer updateAdminInfo2(AdminInfo adminInfo);
    IPage<AdminRoleVo> selectAdmin(AdminInfo adminInfo, TimeQo timeQo, Integer currentPage, Integer limit);
    Integer insertAdmin(AdminInfo adminInfo, String adminPwdAgin)throws InsertException;
    Integer deleteAdmin(Integer adminId ) throws DeleteException;
    List<AdminInfo> selectAdminByRoleId(Integer roleId);
}
