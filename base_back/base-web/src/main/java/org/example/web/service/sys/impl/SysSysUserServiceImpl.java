package org.example.web.service.sys.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.constants.SysConstants;
import org.example.entity.sys.Menu;
import org.example.entity.sys.Role;
import org.example.entity.sys.SysUser;
import org.example.entity.sys.UserRole;
import org.example.web.mapper.sys.MenuMapper;
import org.example.web.mapper.sys.RoleMapper;
import org.example.web.mapper.sys.SysUserMapper;
import org.example.web.mapper.sys.UserRoleMapper;
import org.example.web.service.sys.ISysUserService;
import org.example.web.util.page.PageResult;
import org.example.web.vo.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SysSysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;


    @Override
    public int saveOrUpdateUser(SysUser user) {
        Long id = null;
        if(user.getId() == null || user.getId() == 0) {
            baseMapper.insert(user);
            id = user.getId();
        } else {
            baseMapper.updateById(user);
        }
        if(id != null && id == 0) {
            return 1;
        }
        if(id != null) {
            for(UserRole sysUserRole:user.getUserRoles()) {
                sysUserRole.setUserId( id.longValue());
            }
        } else {
            userRoleMapper.delete(new QueryWrapper<UserRole>().lambda()
                    .eq(UserRole::getUserId,user.getId()));
        }

        for(UserRole sysUserRole:user.getUserRoles()) {
            userRoleMapper.insert(sysUserRole);
        }
        return 1;
    }

    @Override
    public int deleteBatch(List<SysUser> records) {
        for(SysUser user : records) {
            userRoleMapper.delete(new QueryWrapper<UserRole>().lambda()
                    .eq(UserRole::getUserId,user.getId()));
            baseMapper.deleteById(user.getId());
        }
        return 1 ;
    }

    @Override
    public Set<String> findPermissions(String loginName) {
        Set<String> perms = new HashSet<>();
        List<Menu> sysMenus ;
        if(loginName == null || "".equals(loginName) ||
                SysConstants.ADMIN.equalsIgnoreCase(loginName)) {
            sysMenus = menuMapper.selectList(Wrappers.emptyWrapper());
        } else{
            sysMenus = menuMapper.findByUserName (loginName);
        }
        for(Menu sysMenu:sysMenus) {
            if(sysMenu.getPerms() != null && !"".equals(sysMenu.getPerms())) {
                perms.add(sysMenu.getPerms());
            }
        }
        return perms;
    }

    @Override
    public List<UserRole> findUserRoles(Long userId) {
        return userRoleMapper.selectList(new QueryWrapper<UserRole>().lambda()
                .eq(UserRole::getUserId,userId));
    }

    public void findUserRoles(PageResult pageResult) {
        List<?> content = pageResult.getContent();
        for(Object object:content) {
           SysUser sysUser = (SysUser) object;
            List<UserRole> userRoles = findUserRoles(sysUser.getId().longValue());
            sysUser.setUserRoles(userRoles);
            sysUser.setRoleNames(getRoleNames(userRoles));
        }
    }

    @Override
    public void inserLog(SysLog s) {
        menuMapper.insertSysLog(s);
    }

    private String getRoleNames(List<UserRole> userRoles) {
        StringBuilder sb = new StringBuilder();
        for(Iterator<UserRole> iter = userRoles.iterator(); iter.hasNext();) {
            UserRole userRole = iter.next();
            Role sysRole = roleMapper.selectById(userRole.getRoleId());
            if(sysRole == null) {
                continue ;
            }
            sb.append(sysRole.getName());
            if(iter.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
