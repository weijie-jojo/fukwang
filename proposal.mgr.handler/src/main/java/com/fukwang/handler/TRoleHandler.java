package com.fukwang.handler;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fukwang.dto.DataDto;
import com.fukwang.pojo.AdminInfo;
import com.fukwang.pojo.RoleAdminVo;
import com.fukwang.pojo.TDict;
import com.fukwang.pojo.TRole;
import com.fukwang.service.AdminInfoService;
import com.fukwang.service.TRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
public class TRoleHandler {

    @Resource
    private TRoleService tRoleService;
    @Resource
    private AdminInfoService adminInfoService;
    /*
     * 改变角色状态
     *
     * */
    @PostMapping("changeState")
    public DataDto changeState(HttpServletRequest req)  {
        //从前端获取数据
        int roleId=Integer.parseInt(req.getParameter("roleId"));
        int roleState=Integer.parseInt(req.getParameter("roleState"));
        List<AdminInfo> admins=adminInfoService.selectAdminByRoleId(roleId);
        DataDto dto  = new DataDto();
        if(roleState==0){//启用
            tRoleService.updateState1(roleId);
            dto.setId(1);
            dto.success("用户状态变更成功!");
        }else {
            if (admins.size()<=0){
                tRoleService.updateState2(roleId);
                dto.setId(1);
                dto.success("用户状态变更成功!");
            }else {
                dto.setId(0);
                dto.success("此角色已存在用户不可禁用!");
            }
        }
        //流出到客户端
        return dto;
    }
    /*
     * 删除角色信息
     *
     * */
    @PostMapping("delRole")
    public DataDto delRole(HttpServletRequest req){
        int roleId=Integer.parseInt(req.getParameter("roleId"));
        List<AdminInfo> admins=adminInfoService.selectAdminByRoleId(roleId);
        DataDto dto  = new DataDto();
        if(admins.size()<=0){
            tRoleService.delRole(roleId);
            dto.setId(1);
            dto.success("成功删除角色!");
        }else {
            dto.setId(0);
            dto.success("此角色已存在用户不可删除!");
        }
        //流出到客户端
        return dto;
    }
    /*
     * 修改角色信息
     *
     * */
    @PostMapping("editRole")
    public DataDto editRole(HttpServletRequest req){
        int ediRoleId=Integer.parseInt(req.getParameter("ediRoleId"));
        System.out.println("ediRoleId==="+ediRoleId);
        int editRoleState=Integer.parseInt(req.getParameter("editRoleState"));
       String editRoleName=req.getParameter("editRoleName");
        TRole terraceRole=new TRole();
        terraceRole.setRoleId(ediRoleId);
        terraceRole.setRoleName(editRoleName);
        terraceRole.setRoleState(editRoleState);
        tRoleService.editRole(terraceRole);
        DataDto dto  = new DataDto();
        dto.success("成功修改角色!");
        //流出到客户端
        return dto;
    }
    /*
     * 新增角色信息
     *
     * */
    @PostMapping("addRole")
    public DataDto addRole(TRole tRole){
        tRoleService.addRole(tRole);
        DataDto dto  = new DataDto();
        dto.success("成功新增角色!");
        //流出到客户端
        return dto;
    }
    /*
     * 查询字典表中的state信息
     *
     * */
    @GetMapping("initState")
    public DataDto initState(){
        List<TDict>  states=tRoleService.queryState();
        DataDto dto =  new DataDto();
        dto.success(states);
        return dto;
    }
    /*
     * 查询所有角色信息
     *
     * */
    @GetMapping("initRole")
    public DataDto initRole(){
        List<TRole>  roles=tRoleService.queryAll();
        DataDto dto =  new DataDto();
        dto.success(roles);
        return  dto;
    }
    /*
    * 多条件查询角色信息
    *
    * */
    @GetMapping("queryRole")
    public DataDto queryRole( TRole role,HttpServletRequest req){
        //从前端获取数据
        Integer currentPage=Integer.parseInt(req.getParameter("currentPage"));
        Integer limit=Integer.parseInt(req.getParameter("limit"));
        String startTime=req.getParameter("startTime");
        String endTime=req.getParameter("endTime");
        System.out.println("startTime=="+startTime);
        System.out.println("endTime=="+endTime);
        //获取所有用户信息
        IPage<RoleAdminVo> roles=tRoleService.query(role,startTime,endTime,currentPage,limit);

        DataDto dto=new DataDto();
        dto.success(roles.getRecords(),roles.getTotal());
        //流出到客户端
        return dto;
    }


}
