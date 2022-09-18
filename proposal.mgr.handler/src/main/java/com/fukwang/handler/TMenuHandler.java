package com.fukwang.handler;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fukwang.dto.DataDto;
import com.fukwang.pojo.AdminInfo;
import com.fukwang.pojo.TMenu;
import com.fukwang.qo.TimeQo;
import com.fukwang.service.TMenuService;
import com.fukwang.vo.AdminRoleVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class TMenuHandler {
    @Resource
    private TMenuService tMenuService;
    /*
     *
     * 根据登录的角色id获取菜单
     * */
    @GetMapping("/getMenus")
    public DataDto<TMenu> getMenuList(HttpSession session) {
        AdminInfo adminInfo = (AdminInfo) session.getAttribute("admin");
        DataDto<TMenu> dto = new DataDto<>();
        if (adminInfo == null) {
            dto.err("未登录，请重试");
            return null;
        }
        List<TMenu> menuInfoList = tMenuService.getMenuList(adminInfo.getRoleId());
        dto.success(menuInfoList);
        return dto;
    }
    /*
     *
     * 根据选择的角色id获取菜单
     * */
    @GetMapping("/selectMenuById")
    public DataDto<TMenu> selectMenuById(Integer roleId) {
        DataDto<TMenu> dto = new DataDto<>();
        List<TMenu> menuInfoList = tMenuService.getMenuList(roleId);
        dto.success(menuInfoList);
        return dto;
    }
    /*
     *
     * 查询所有父菜单
     * */
    @GetMapping("/selectPmenus")
    public DataDto<TMenu> selectPmenus() {
        DataDto<TMenu> dto = new DataDto<>();
        List<TMenu> pmenus = tMenuService.selectPmenus();
        dto.success(pmenus);
        return dto;
    }
    /*
    *
    * 条件查询菜单
    * */
    @GetMapping("/selectMenu")
    public DataDto selectAdmin(String menuName, Integer menuState,TimeQo timeQo, Integer currentPage, Integer limit){

        IPage<TMenu> menuIPage = tMenuService.selectMenu(menuName,menuState,timeQo,currentPage,limit);
        DataDto<TMenu> dataDto = new DataDto<>();
        dataDto.success(menuIPage.getRecords(),menuIPage.getTotal());
        return dataDto;
    }
    /*
     *
     * 新增菜单
     * */
    @PostMapping("/insertMenu")
    public DataDto insertMenu(TMenu tMenu){
        DataDto dataDto=new DataDto();
        tMenuService.insertMenu(tMenu);
        dataDto.setId(0);
        dataDto.setMessage("菜单新增成功");
        return  dataDto;
    }
    /*
     *
     * 修改菜单信息
     *
     * */
    @PostMapping("/updateMenu")
    public DataDto updateAdmin(TMenu tMenu){
        DataDto dataDto=new DataDto();
        tMenuService.updateMenu(tMenu);
        dataDto.success("修改菜单信息成功");
        return  dataDto;
    }
    /**
     *删除用户
     */
    @PostMapping("/deleteMenu")
    public DataDto deleteAdmin(Integer menuId){
        DataDto dataDto=new DataDto();
        tMenuService.deleteMenu(menuId);
        dataDto.success("删除成功");
        return  dataDto;
    }

    @GetMapping("/seMeByRIdNo")
    public DataDto<TMenu> seMeByRIdNo(Integer roleId){
        List<TMenu> tMenuAllList = tMenuService.selectAllMenu(roleId);
        DataDto<TMenu> dataDto = new DataDto<>();
        System.out.println(tMenuAllList);
        dataDto.success(tMenuAllList);
        return dataDto;
    }

    @PostMapping("/addTerraceMenu")
    public DataDto<Integer> addTerraceMenu(Integer roleId,Integer[] checkNode){
        for (int i = 0; i < checkNode.length; i++) {
            tMenuService.addRmMenu(roleId, checkNode[i]);
        }
        DataDto<Integer> dto = new DataDto<>();
        dto.success("添加成功");
        return dto;
    }

    @PostMapping("/deleteTerraceMenu")
    public DataDto<Integer> deleteTerraceMenu(Integer roleId, String checkNode) {
        System.out.println(checkNode);
        System.out.println("删除数组");
        List<Integer> checks = JSONArray.parseArray(checkNode, Integer.class);
        for (Integer check : checks) {
            tMenuService.deleteRmMenu(roleId, check);
        }
        DataDto<Integer> dto = new DataDto<>();
        dto.success("删除成功");
        return dto;
    }
}
