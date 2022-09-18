package com.fukwang.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fukwang.exception.DeleteException;
import com.fukwang.exception.GetException;
import com.fukwang.exception.InsertException;
import com.fukwang.exception.UpdateException;
import com.fukwang.mapper.TDictMapper;
import com.fukwang.mapper.TRoleMapper;
import com.fukwang.pojo.RoleAdminVo;
import com.fukwang.pojo.TDict;
import com.fukwang.pojo.TRole;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TRoleServiceImpl implements TRoleService {

    @Resource
    private TRoleMapper tRoleMapper;
    @Resource
    private TDictMapper tDictMapper;

    /*
     * 新增角色信息
     *
     * */
    @Override
    public int addRole( TRole tRole) throws InsertException {

        TRole tRole2=new TRole();
        Date date=new Date();
        tRole2.setGmtCreate(date);
        tRole2.setRoleName(tRole.getRoleName());
        tRole2.setRoleState(tRole.getRoleState());
        Integer rs = tRoleMapper.insert(tRole2);
        if (rs < 1) {
            throw new InsertException("新增角色失败");
        }
        return rs;
    }
    /*
    * 修改角色状态(启用)
    * */
    @Override
    public int updateState1(int roleId ) throws UpdateException {
        //更新的条件
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("role_id", roleId);
        updateWrapper.set("role_state", "1");
        //执行更新操作
        int result = this.tRoleMapper.update(null, updateWrapper);
        if (result < 1) {
            throw new UpdateException("修改状态失败");
        }
        return result;
    }
    /*
     * 修改角色状态(禁用)
     * */
    @Override
    public int updateState2(int roleId ) throws UpdateException {
        //更新的条件
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("role_id", roleId);
        updateWrapper.set("role_state", "0");
        //执行更新操作
        int result = this.tRoleMapper.update(null, updateWrapper);
        if (result < 1) {
            throw new UpdateException("修改状态失败");
        }
        return result;
    }
    /*
     * 修改角色信息
     *
     * */
    @Override
    public int editRole(TRole tRole) throws UpdateException {
        UpdateWrapper<TRole> updateWrapper = new UpdateWrapper();

        updateWrapper.eq("role_id",tRole.getRoleId());
        updateWrapper.set("role_name",tRole.getRoleName());
        updateWrapper.set("role_state",tRole.getRoleState());
        Integer rs = tRoleMapper.update(null,updateWrapper);
        if (rs < 1) {
            throw new UpdateException("修改角色信息失败");
        }
        return rs;
    }
    /*
     * 删除角色信息
     *
     * */
    @Override
    public int delRole(int roleId) throws DeleteException {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("role_id",roleId);
        Integer rs = tRoleMapper.delete(queryWrapper);
        if (rs < 1) {
            throw new DeleteException("删除角色信息失败");
        }
        return rs;
    }


    /*
     * 查询字典表中的state信息
     *
     * */
    @Override
    public List<TDict> queryState(){
        QueryWrapper<TDict> queryWrapper=new QueryWrapper();
        queryWrapper.eq("type_alias","state");
        return tDictMapper.selectList(queryWrapper);
    }
    /*
     * 查询所有角色信息
     *
     * */
    @Override
    public List<TRole> queryAll(){
        QueryWrapper<TRole> queryWrapper=new QueryWrapper();
        return tRoleMapper.selectList(queryWrapper);
    }
    /*
    * 条件查询角色信息
    *
    * */
    @Override
    public IPage<RoleAdminVo> query(TRole role, String bdate, String edate, Integer currentPage, Integer limit) throws GetException {

        if(!StringUtils.hasText(bdate)){
            bdate=null;
        }
        if(!StringUtils.hasText(edate)){
            edate=null;
        }
        IPage<RoleAdminVo> page = tRoleMapper.selectJoinPage(new Page<>(currentPage,limit), RoleAdminVo.class,
                new MPJQueryWrapper<TRole>()
                        .selectAll(TRole.class)
                        .eq(role.getRoleId()!=null,"t.role_id",role.getRoleId())
                        .eq(role.getRoleState()!=null,"t.role_state",role.getRoleState())
                        .ge(bdate!=null,"t.gmt_create",bdate)
                        .le(edate!=null,"t.gmt_create",edate));
        return page;
    }
//    /*
//     * 查询总角色数
//     *
//     * */
//    @Override
//    public Integer getCount(TRole role, String bdate, String edate)  throws GetException{
////        if(roleId==null||roleId<1){
////            roleId=null;
////        }
////        if(roleState==null||roleState<0){
////            roleState=null;
////        }
//        if(!StringUtils.hasText(bdate)){
//            bdate=null;
//        }
//        if(!StringUtils.hasText(edate)){
//            edate=null;
//        }
//
//        List<TRole> roleList = tRoleMapper.selectJoinList( TRole.class,
//                new MPJQueryWrapper<TRole>()
//                        .selectAll(TRole.class)
//                        .eq(role.getRoleId()!=null,"t.role_id",role.getRoleId())
//                        .eq(role.getRoleState()!=null,"t.role_state",role.getRoleState())
//                        .ge(bdate!=null,"t.gmt_create",bdate)
//                        .le(edate!=null,"t.gmt_create",edate));
//        Integer count=roleList.size();
//
//        return count;
//    }

}
