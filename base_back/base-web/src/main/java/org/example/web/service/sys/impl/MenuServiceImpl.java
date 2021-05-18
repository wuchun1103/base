package org.example.web.service.sys.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.constants.SysConstants;
import org.example.common.utils.CommonUtil;
import org.example.entity.sys.Menu;
import org.example.web.mapper.sys.MenuMapper;
import org.example.web.service.sys.IMenuService;
import org.example.web.util.ShiroUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public int saveOrUpdateMenu(Menu record) {
        if(record.getId() == null || record.getId() == 0) {
            if(!CommonUtil.isEmpty(ShiroUtils.getUser())){
                record.setCreateBy(ShiroUtils.getUser().getUserName());
                record.setUpdateBy(ShiroUtils.getUser().getUserName());
            }
            record.setUpdateTime(new Date());
            record.setCreateTime(new Date());
            return baseMapper.insert(record);
        }
        if(record.getParentId() == null) {
            record.setParentId(0L);
        }
        if(!CommonUtil.isEmpty(ShiroUtils.getUser())){
            record.setUpdateBy(ShiroUtils.getUser().getUserName());
        }
        record.setUpdateTime(new Date());
        return baseMapper.updateById(record);
    }

    @Override
    public List<Menu> findTree(String userName, int menuType) {
        List<Menu> sysMenus = new ArrayList<>();
        List<Menu> menus = findByUser(userName);
        for (Menu menu : menus) {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                menu.setLevel(0);
                if(!exists(sysMenus, menu)) {
                    sysMenus.add(menu);
                }
            }
        }
        sysMenus.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
        findChildren(sysMenus, menus, menuType);
        return sysMenus;
    }

    @Override
    public List<Menu> findByUser(String userName) {
        if(userName == null || "".equals(userName) || SysConstants.ADMIN
                .equalsIgnoreCase(userName)) {
            return  baseMapper.selectList(Wrappers.emptyWrapper());
        }
        return baseMapper.findByUserName(userName);
    }

    private void findChildren(List<Menu> SysMenus, List<Menu> menus, int menuType) {
        for (Menu sysMenu : SysMenus) {
            List<Menu> children = new ArrayList<>();
            for (Menu menu : menus) {
                if(menuType == 1 && menu.getType() == 2) {
                    continue ;
                }
                if (sysMenu.getId() != null && sysMenu.getId().equals(menu.getParentId())) {
                    menu.setParentName(sysMenu.getName());
                    menu.setLevel(sysMenu.getLevel() + 1);
                    if(!exists(children, menu)) {
                        children.add(menu);
                    }
                }
            }
            sysMenu.setChildren(children);
            children.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
            findChildren(children, menus, menuType);
        }
    }

    private boolean exists(List<Menu> sysMenus, Menu sysMenu) {
        boolean exist = false;
        for(Menu menu:sysMenus) {
            if(menu.getId().equals(sysMenu.getId())) {
                exist = true;
            }
        }
        return exist;
    }
}
