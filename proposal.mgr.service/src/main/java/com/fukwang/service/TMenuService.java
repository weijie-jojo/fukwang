package com.fukwang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fukwang.exception.DeleteException;
import com.fukwang.exception.GetException;
import com.fukwang.exception.InsertException;
import com.fukwang.exception.UpdateException;
import com.fukwang.pojo.TMenu;
import com.fukwang.qo.TimeQo;
import com.fukwang.vo.AdminLogVo;


import java.util.List;

public interface TMenuService {
    List<TMenu> getMenuList(Integer roleId) throws GetException;
    IPage<TMenu> selectMenu(String menuName, Integer menuState, TimeQo timeQo, Integer currentPage, Integer limit);
    List<TMenu> selectPmenus() throws GetException;
    Integer insertMenu(TMenu tMenu)throws InsertException;
    Integer updateMenu(TMenu tMenu)throws UpdateException;
    Integer deleteMenu(Integer menuId ) throws DeleteException;
    List<TMenu> selectAllMenu(Integer RoleId) throws GetException;//查找所有的权限
    Integer addRmMenu(Integer RoleId, Integer menuId) throws InsertException;
    Integer deleteRmMenu(Integer RoleId, Integer menuId) throws DeleteException;
}
