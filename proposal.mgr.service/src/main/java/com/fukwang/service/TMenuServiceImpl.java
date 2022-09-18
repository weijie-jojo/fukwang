package com.fukwang.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fukwang.exception.DeleteException;
import com.fukwang.exception.GetException;
import com.fukwang.exception.InsertException;
import com.fukwang.exception.UpdateException;
import com.fukwang.mapper.TMenuMapper;
import com.fukwang.pojo.AdminInfo;
import com.fukwang.pojo.AdminLog;
import com.fukwang.pojo.TMenu;
import com.fukwang.qo.TimeQo;
import com.fukwang.util.JudgeNull;
import com.fukwang.util.MD5;
import com.fukwang.vo.AdminLogVo;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TMenuServiceImpl implements TMenuService {
    @Resource
    private TMenuMapper tMenuMapper;
    /*
     *
     * 根据登录id获取菜单
     * */
    @Override
    public List<TMenu> getMenuList(Integer roleId) throws GetException {
        List<TMenu> list = tMenuMapper.selectJoinList(TMenu.class,
                new MPJQueryWrapper<TMenu>()
                        .selectAll(TMenu.class)
                        .innerJoin("t_role_menu B ON  t.menu_id=B.menu_id")
                        .eq(roleId>0,"B.role_id",roleId));
        return list;

    }
    /*
     *
     * 查询所有父菜单
     * */
    @Override
    public List<TMenu> selectPmenus() throws GetException {
        return tMenuMapper.selectList(new QueryWrapper<TMenu>()
        .eq("menu_pid",0));
    }
    /*
     * 条件查询菜单
     * */
    @Override
    public IPage<TMenu> selectMenu(String menuName, Integer menuState,TimeQo timeQo, Integer currentPage, Integer limit) {
        timeQo.setStartTime(JudgeNull.isNull(timeQo.getStartTime()));
        timeQo.setEndTime(JudgeNull.isNull(timeQo.getEndTime()));
        String menuNames=JudgeNull.isNull(menuName);
        Integer menuStates=JudgeNull.isNull(menuState);
        Integer currentPages=JudgeNull.isNull(currentPage);
        Integer limits=JudgeNull.isNull(limit);
        System.out.println("menuNames=="+menuNames);
        System.out.println("getStartTime=="+timeQo.getStartTime());
        System.out.println("getEndTime=="+timeQo.getEndTime());
        System.out.println("currentPages=="+currentPages);
        System.out.println("limits=="+limits);
//        IPage<TMenu> menuIPage=new Page<>(currentPages,limits);
//        MPJQueryWrapper mpjQueryWrapper=new MPJQueryWrapper();
//        mpjQueryWrapper.
        IPage<TMenu> menuIPage = tMenuMapper.selectPage(new Page<>(currentPages,limits),
                new QueryWrapper<TMenu>()
                        .like(menuNames!=null,"menu_name",menuNames)
                        .eq(menuStates!=null,"menu_state",menuStates)
                        .ge(timeQo.getStartTime()!=null,"gmt_create",timeQo.getStartTime())
                        .le(timeQo.getEndTime()!=null,"gmt_create",timeQo.getEndTime()));
        return menuIPage;
    }
    /*
     * 新增菜单
     * */
    @Override
    public Integer insertMenu(TMenu tMenu)throws InsertException {

        Integer count=tMenuMapper.insert(tMenu);
        if (count<0){
            throw  new InsertException("新增菜单失败！");
        }
        return count;
    }
    /*
     * 修改菜单
     * */
    @Override
    public Integer updateMenu(TMenu tMenu) throws UpdateException {
        UpdateWrapper<TMenu> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("menu_id",tMenu.getMenuId());
        updateWrapper.set("menu_name",tMenu.getMenuName());
        updateWrapper.set("menu_state",tMenu.getMenuState());
        Integer count=tMenuMapper.update(null,updateWrapper);
        if (count<0){
            throw  new InsertException("修改菜单失败！");
        }
        return count;
    }
    /*
     * 删除菜单
     * */
    @Override
    public Integer deleteMenu(Integer menuId) throws DeleteException {
        Integer count=tMenuMapper.delete(new UpdateWrapper<TMenu>().eq("menu_id",menuId));
        if (count<0){
            throw  new InsertException("删除菜单失败！");
        }
        return count;

    }

    @Override
    public List<TMenu> selectAllMenu(Integer RoleId) throws GetException {
        List<TMenu>  tMenuList = tMenuMapper.selectPidMenu();//所有菜单
        List<TMenu>    haveNotMenuList =  tMenuMapper.selectAllMenu(RoleId);
        HashSet<TMenu> chang = new HashSet<>();
        List<TMenu>   addList = new ArrayList<>();
        for(TMenu m:haveNotMenuList){
            int count =0;
            for(TMenu n:haveNotMenuList){
                if(m.getMenuPid()==n.getMenuId()){
                    continue;
                }else{
                    count++;
                }
            }
            if(count==haveNotMenuList.size()){
                for(TMenu p :tMenuList){
                    if(m.getMenuPid()==p.getMenuId()){
                        chang.add(p);
                        break;
                    }
                }
            }
        }
        for(TMenu k:haveNotMenuList){
            chang.add(k);
        }
        addList.clear();
        addList.addAll(chang);
        return addList;
    }

    @Override
    public Integer addRmMenu(Integer RoleId, Integer menuId) throws InsertException {
        TMenu tMenu = new TMenu();
        int count=0;
        List<TMenu>  terraceMenuList = tMenuMapper.selectPidMenu();//查找所有菜单
        //查找父级菜单ID
        for(TMenu m:terraceMenuList){
            if(m.getMenuId()==menuId){
                tMenu = m;//获取本菜单对象
            }
        }
        //查找父菜单是否在关系表中
        List<TMenu>  haveMenuList =  tMenuMapper.selectMenuByRoleId(RoleId);//查找已有菜单

        for(TMenu n:haveMenuList){
            if(n.getMenuId()==tMenu.getMenuPid()){
                break;
            }
            count++;
        }

        Integer rs=0;
        Integer updatePd = 0;


        if(count==haveMenuList.size()){//如果父级菜单不存在

            if( tMenu.getMenuPid()!=0) {//如果父级菜单不是0 则新增父级菜单
                updatePd = tMenuMapper.addRmMenu(RoleId, tMenu.getMenuPid());
            }
            int countid = 0;
            for(TMenu n:haveMenuList){
                if(n.getMenuId()==tMenu.getMenuId())
                {
                    break;
                }
                countid++;
            }
            if(countid==haveMenuList.size()){
                rs = tMenuMapper.addRmMenu(RoleId, menuId);
            }

        }else {//如果父级菜单存在
            int countid = 0;
            for(TMenu n:haveMenuList){
                if(n.getMenuId()==tMenu.getMenuId()){
                    break;
                }
                countid++;
            }
            if(countid==haveMenuList.size()){
                rs = tMenuMapper.addRmMenu(RoleId, menuId);
            }
        }
        return rs;
    }

    @Override
    public Integer deleteRmMenu(Integer RoleId, Integer menuId) throws DeleteException {
        System.out.println(menuId + "test");
        Integer rs = tMenuMapper.deleteRmMenu(RoleId, menuId);
        if (rs < 1) {
            throw new DeleteException("删除角色菜单失败");
        }
        return rs;
    }
}
