package com.fukwang.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fukwang.exception.DeleteException;
import com.fukwang.exception.InsertException;
import com.fukwang.exception.UpdateException;
import com.fukwang.pojo.RoleAdminVo;
import com.fukwang.pojo.TDict;
import com.fukwang.pojo.TRole;


import java.util.List;

public interface TRoleService {


     IPage<RoleAdminVo> query(TRole role, String bdate, String edate, Integer currentPage, Integer limit);

//     Integer getCount(TRole role, String bdate, String edate);

     List<TRole> queryAll();

     List<TDict> queryState();

     int addRole(TRole tRole) throws InsertException;

     int updateState1(int roleId) throws UpdateException;
     int updateState2(int roleId) throws UpdateException;

     int editRole(TRole terraceRole) throws UpdateException;

     int delRole(int roleId) throws DeleteException;


}
