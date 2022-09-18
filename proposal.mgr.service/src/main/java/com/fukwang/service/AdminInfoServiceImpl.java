package com.fukwang.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fukwang.exception.DeleteException;
import com.fukwang.exception.InsertException;
import com.fukwang.exception.UpdateException;
import com.fukwang.mapper.AdminInfoMapper;
import com.fukwang.exception.LoginException;
import com.fukwang.pojo.AdminInfo;
import com.fukwang.pojo.TProposal;
import com.fukwang.pojo.TRole;
import com.fukwang.qo.LoginQo;
import com.fukwang.qo.TimeQo;
import com.fukwang.util.JudgeNull;
import com.fukwang.util.MD5;
import com.fukwang.vo.AdminRoleVo;
import com.fukwang.vo.TProposalVo;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class AdminInfoServiceImpl implements AdminInfoService{
    @Resource
    private AdminInfoMapper adminInfoMapper;

    @Override
    public AdminInfo login(LoginQo qo, String code) throws LoginException {
        //  校验验证码
        if (!code.equalsIgnoreCase(qo.getCode())) {
            throw new LoginException("验证码错误");
        }
        //获取用户信息
        QueryWrapper<AdminInfo> queryByCount=new QueryWrapper<AdminInfo>();
        queryByCount.eq("admin_account",qo.getAccount());
        AdminInfo admin = adminInfoMapper.selectOne(queryByCount);
        if (admin == null) {
            //找不到用户就是账户错了
            throw new LoginException("账号输入错误");
        }
        if (admin.getAdminState() == 0) {
            throw new LoginException("此账户已被禁用");
        }
        MD5 md5 = new MD5();

        try {
            if (!md5.EncoderByMd5Many(qo.getPwd(), 10).equals(admin.getAdminPwd())) {
                throw new LoginException("密码输入错误");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return admin;

    }
    /*
    *
    * 查询用户信息（通过用户id查询）
    * */
    @Override
    public List<AdminRoleVo> selectAdminRoleById(Integer adminId) {
        List<AdminRoleVo> adminRoleVo = adminInfoMapper.selectJoinList(AdminRoleVo.class,
                new MPJQueryWrapper<AdminInfo>()
                        .selectAll(AdminInfo.class)
                        .select("B.role_id")
                        .select("B.role_name")
                        .select("B.role_state")
                        .select("B.gmt_create")
                        .innerJoin("t_role B on t.role_id=B.role_id")
                        .eq("admin_id",adminId));
        return adminRoleVo;
    }
    /*
     *
     * 查询用户信息（通过角色id查询）
     * */
    @Override
    public List<AdminInfo> selectAdminByRoleId(Integer roleId) {
        QueryWrapper<AdminInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        List<AdminInfo> admins=adminInfoMapper.selectList(queryWrapper);
        return admins;
    }
    /*
     *
     * 修改用户信息（个人中心）
     *
     * */
    public  Integer updateAdminInfo(AdminInfo adminInfo){
        UpdateWrapper<AdminInfo> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("admin_id",adminInfo.getAdminId());
        updateWrapper.set(adminInfo.getAdminName()!=null,"admin_name",adminInfo.getAdminName());
        updateWrapper.set(adminInfo.getAdminImage()!=null,"admin_image",adminInfo.getAdminImage());
        updateWrapper.set(adminInfo.getAdminTel()!=null,"admin_tel",adminInfo.getAdminTel());
        updateWrapper.set(adminInfo.getRoleId()!=null,"role_id",adminInfo.getRoleId());
        updateWrapper.set(adminInfo.getAdminState()!=null,"admin_state",adminInfo.getAdminState());
        Integer count=adminInfoMapper.update(null,updateWrapper);
        if (count<0){
            throw  new UpdateException("修改用户信息失败！");
        }
        return count;
    }
//    /*
//     *
//     * 修改用户信息(用户管理)
//     *
//     * */
//    public  Integer updateAdminInfo2(AdminInfo adminInfo){
//        UpdateWrapper<AdminInfo> updateWrapper=new UpdateWrapper<>();
//        updateWrapper.eq("admin_id",adminInfo.getAdminId());
//        updateWrapper.set(adminInfo.getAdminName()!=null,"admin_name",adminInfo.getAdminName());
//        updateWrapper.set(adminInfo.getAdminImage()!=null,"admin_image",adminInfo.getAdminImage());
//        updateWrapper.set(adminInfo.getAdminTel()!=null,"admin_tel",adminInfo.getAdminTel());
//        updateWrapper.set(adminInfo.getRoleId()!=null,"role_id",adminInfo.getRoleId());
//        updateWrapper.set(adminInfo.getAdminState()!=null,"admin_state",adminInfo.getAdminState());
//        Integer count=adminInfoMapper.update(null,updateWrapper);
//        if (count<0){
//            throw  new UpdateException("修改用户信息失败！");
//        }
//        return count;
//    }
    /*
    *
    * 修改用户密码
    *
    * */
    @Override
    public Integer updateAdmin(String pass,Integer adminId,Integer typeId)throws UpdateException {

        Integer count=null;
        if (typeId==1){
            try {
                String md5Pwd = new MD5().EncoderByMd5Many("123456", 10);
                UpdateWrapper<AdminInfo> updateWrapper=new UpdateWrapper<>();
                updateWrapper.eq("admin_id",adminId);
                updateWrapper.set("admin_pwd",md5Pwd);
                count=adminInfoMapper.update(null,updateWrapper);
                if (count<0){
                    throw  new UpdateException("重置密码失败！");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        if (typeId==2){
            UpdateWrapper<AdminInfo> updateWrapper=new UpdateWrapper<>();
            updateWrapper.eq("admin_id",adminId);
            updateWrapper.set("admin_pwd",pass);
            count=adminInfoMapper.update(null,updateWrapper);
            if (count<0){
                throw  new UpdateException("修改角色和状态失败！");
            }
        }
        return count;
    }
    /*
     * 多条件查询用户信息
     *
     * */
    @Override
    public IPage<AdminRoleVo> selectAdmin(AdminInfo adminInfo, TimeQo timeQo, Integer currentPage, Integer limit) {
        adminInfo.setAdminName(JudgeNull.isNull(adminInfo.getAdminName()));
        adminInfo.setAdminState(JudgeNull.isNull(adminInfo.getAdminState()));
        timeQo.setStartTime(JudgeNull.isNull(timeQo.getStartTime()));
        timeQo.setEndTime(JudgeNull.isNull(timeQo.getEndTime()));
        Integer currentPages=JudgeNull.isNull(currentPage);
        Integer limits=JudgeNull.isNull(limit);
        System.out.println("getAdminName=="+adminInfo.getAdminName());
        System.out.println("getAdminState=="+adminInfo.getAdminState());
        System.out.println("getStartTime=="+timeQo.getStartTime());
        System.out.println("getEndTime=="+timeQo.getEndTime());
        System.out.println("currentPages=="+currentPages);
        System.out.println("limits=="+limits);
        IPage<AdminRoleVo> adminInfoIPage = adminInfoMapper.selectJoinPage(new Page<>(currentPages,limits), AdminRoleVo.class,
                new MPJQueryWrapper<AdminInfo>()
                        .selectAll(AdminInfo.class)
                        .select("B.role_name")
                        .innerJoin("t_role B on t.role_id=B.role_id")
                        .like(adminInfo.getAdminName()!=null,"t.admin_name",adminInfo.getAdminName())
                        .eq(adminInfo.getAdminState()!=null,"t.admin_state",adminInfo.getAdminState())
                        .ge(timeQo.getStartTime()!=null,"t.gmt_create",timeQo.getStartTime())
                        .le(timeQo.getEndTime()!=null,"t.gmt_create",timeQo.getEndTime()));
        return adminInfoIPage;
    }
    @Override
    public Integer insertAdmin(AdminInfo adminInfo, String adminPwdAgin)throws InsertException {
       //查重
        QueryWrapper<AdminInfo>  queryWrapper=new QueryWrapper<>();
        List<AdminInfo> adminInfos=adminInfoMapper.selectList(queryWrapper);
        for(AdminInfo admin : adminInfos){
            if (admin.getAdminAccount().equals(adminInfo.getAdminAccount())){
                throw new InsertException("账号已存在！");
            }
        }
        Integer count=0;
        MD5 md5 = new MD5();
        if (adminInfo.getAdminPwd().equals(adminPwdAgin)){
            try {
                //md5加密
                adminInfo.setAdminPwd(md5.EncoderByMd5Many(adminInfo.getAdminPwd(), 10));
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            count=adminInfoMapper.insert(adminInfo);
            if (count<0){
                throw  new InsertException("新增用户失败！");
            }
        }else {
            throw  new InsertException("两次密码不一致");
        }
        return count;
    }
    /**
     *删除用户
     */
    @Override
    public Integer deleteAdmin(Integer adminId) throws DeleteException {
        QueryWrapper<AdminInfo>  queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("admin_id",adminId);
        Integer count=adminInfoMapper.delete(queryWrapper);
        if(count<0){
            throw  new DeleteException("删除失败！");
        }
        return count;
    }

}
