package com.fukwang.mapper;


import com.fukwang.pojo.TMenu;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TMenuMapper extends MPJBaseMapper<TMenu> {
//    List<TMenu> selectByRoleMenuInfos(@Param("terraceRoleMenuList") List<TerraceRoleMenu> terraceRoleMenuList);

    /**
     * 获取所有有子菜单的父菜单列表
     * @return 父菜单列表
     */
//    List<TMenu> selectByPid();
//
//    List<TMenu> selectByPid2(@Param("menuPid") Integer menuPid);

//    List<TMenu> selectMenuList(@Param("terraceMenu") TMenu tMenu,
//                                     @Param("pageQo") PageQo pageQo);
//
//    Integer countMenuList(@Param("terraceMenu") TerraceMenu terraceMenu);

//    Integer insertMenu(TMenu tMenu);
//
//    Integer updateMenu(TMenu tMenu);
//
//    Integer delMenu(@Param("menuId") Integer menuId);
//
//    List<TMenu> selectAllRole();//查看所有的角色

    List<TMenu> selectMenuByRoleId(Integer roleId);//根据角色Id查找权限

    List<TMenu> selectAllMenu(Integer roleId);//查找没有的权限

    List<TMenu> selectPidMenu();//查找所有菜单

    Integer deleteRmMenu(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);//删除权限

    Integer addRmMenu(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);//添加权限
}
