package com.fukwang.handler;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fukwang.dto.DataDto;
import com.fukwang.dto.RouteDto;
import com.fukwang.pojo.AdminInfo;
import com.fukwang.qo.AdminInfoQo;
import com.fukwang.qo.LoginQo;
import com.fukwang.qo.TimeQo;
import com.fukwang.service.AdminInfoService;

import com.fukwang.util.MD5;
import com.fukwang.vo.AdminRoleVo;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminHandler{
    @Resource
    private AdminInfoService adminInfoService;
    /*
     * 登录
     *
     * */
    @PostMapping("/doLogin")
    public RouteDto doLogin(LoginQo qo, HttpSession session){
        RouteDto dto = new RouteDto();
//        session.setMaxInactiveInterval(1200);
        AdminInfo admin = adminInfoService.login(qo, (String) session.getAttribute("code"));
//        if (admin != null) {
//            session.setAttribute("loginUserId", admin.getAdminId());
//            redisTemplate.opsForValue().set("loginUser:" + admin.getAdminId(), session.getId());
//            return new ReturnData(StatusCode.REQUEST_SUCCESS, user, "登录成功！");
//        }
//        else {
//            throw new MyException(StatusCode.ACCOUNT_OR_PASSWORD_ERROR, "账户名或密码错误！");
//        }
        session.setAttribute("admin", admin);
        dto.setId(0);
        dto.setMessage("登录成功");
        dto.setLocation("main");
        dto.setObj(admin);
        return dto;
    }
    /*
    * 退出
    *
    * */
    @PostMapping("/doExit")
    public RouteDto doExit(HttpSession session){
        session.invalidate();
        RouteDto routeDto=new RouteDto();
        routeDto.setId(0);
        routeDto.setLocation("/proposal_mgr");
        return routeDto;
    }
    /**
     *
     * @param session
     * 从session中获取 Terraceadmin 信息
     * 传出对象集合
     * @return
     */
    @GetMapping("/selectAdminRoleVo")
    public DataDto selectAdminRoleVo(HttpSession session){
        AdminInfo admin = (AdminInfo)session.getAttribute("admin");
        List<AdminRoleVo> adminRoleVos = adminInfoService.selectAdminRoleById(admin.getAdminId());
        DataDto<AdminRoleVo> dataDto = new DataDto<>();
        dataDto.success(adminRoleVos);
        return dataDto;
    }
    /*
    *
    * 修改密码
    *
    * */
    @PostMapping("/changAdminPwd")
    public DataDto changAdminPwd(HttpServletRequest req) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String pass=req.getParameter("pass");
        Integer adminId=Integer.parseInt(req.getParameter("adminId"));
        System.out.println("调用修改密码"+pass+"...."+adminId);
        MD5 md5 = new MD5();
        pass = md5.EncoderByMd5Many(pass,10);
        System.out.println("md5pass==="+pass);
        DataDto dataDto=new DataDto();
        adminInfoService.updateAdmin(pass,adminId,2);
        dataDto.success("修改密码成功");
        return  dataDto;
    }
    /*
     *
     * 修改用户信息（个人中心）
     *
     * */
    @PostMapping("/updateAdmin")
    public DataDto updateAdmin(AdminInfo adminInfo){
        DataDto dataDto=new DataDto();
        adminInfoService.updateAdminInfo(adminInfo);
        dataDto.success("修改用户信息成功");
        return  dataDto;
    }
//    /*
//     *
//     * 修改用户信息（用户管理）
//     *
//     * */
//    @PostMapping("/updateAdmin2")
//    public DataDto updateAdmin2(AdminInfo adminInfo){
//        DataDto dataDto=new DataDto();
//        adminInfoService.updateAdminInfo2(adminInfo);
//        dataDto.success("修改用户信息成功");
//        return  dataDto;
//    }
    /**
     *条件查询用户信息
     */
    @GetMapping("/selectAdmin")
    public DataDto selectAdmin(AdminInfo adminInfo, TimeQo timeQo, HttpServletRequest req){
        Integer currentPage=Integer.parseInt(req.getParameter("currentPage"));
        Integer limit=Integer.parseInt(req.getParameter("limit"));
        IPage<AdminRoleVo> adminRoleVoIPage = adminInfoService.selectAdmin(adminInfo,timeQo,currentPage,limit);
        DataDto<AdminRoleVo> dataDto = new DataDto<>();
        dataDto.success(adminRoleVoIPage.getRecords(),adminRoleVoIPage.getTotal());
        return dataDto;
    }

    /**
     *新增用户
     */
    @PostMapping("/insertAdmin")
    public DataDto insertAdmin(AdminInfo adminInfo,String adminPwdAgin){
        DataDto dataDto=new DataDto();
        adminInfoService.insertAdmin(adminInfo,adminPwdAgin);
        dataDto.setId(0);
        dataDto.setMessage("新增成功");
        return  dataDto;
    }
    /**
     *删除用户
     */
    @PostMapping("/deleteAdmin")
    public DataDto deleteAdmin(Integer adminId){
        DataDto dataDto=new DataDto();
        adminInfoService.deleteAdmin(adminId);
        dataDto.success("删除成功");
        return  dataDto;
    }


}
