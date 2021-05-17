package org.example.web.service.sys.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.constants.SysConstants;
import org.example.common.utils.CommonUtil;
import org.example.entity.sys.Menu;
import org.example.entity.sys.Role;
import org.example.entity.sys.RoleMenu;
import org.example.web.mapper.sys.MenuMapper;
import org.example.web.mapper.sys.RoleMapper;
import org.example.web.mapper.sys.RoleMenuMapper;
import org.example.web.service.sys.IRoleService;
import org.example.web.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<Menu> findRoleMenus(Long roleId) {
        Role sysRole = baseMapper.selectById(roleId);
        if(SysConstants.ADMIN.equalsIgnoreCase(sysRole.getName())) {
            return menuMapper.selectList(Wrappers.emptyWrapper());
        }
        return menuMapper.findRoleMenus(roleId);
    }

    @Override
    public int saveRoleMenus(List<RoleMenu> records) {
        if(records == null || records.isEmpty()) {
            return 1;
        }
        Long roleId = records.get(0).getRoleId();

        roleMenuMapper.delete(new QueryWrapper<RoleMenu>().lambda()
        .eq(RoleMenu::getRoleId,roleId));

        for( RoleMenu record:records) {
            if(!CommonUtil.isEmpty(ShiroUtils.getUser())){
                record.setCreateBy(ShiroUtils.getUser().getUserName());
                record.setUpdateBy(ShiroUtils.getUser().getUserName());
            }
            record.setCreateTime(new Date());
            record.setUpdateTime(new Date());
            roleMenuMapper.insert(record);
        }
        return 1;
    }
}
