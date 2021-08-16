package org.example.web.service.sys;


import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.sys.SysUser;
import org.example.entity.sys.UserRole;
import org.example.web.util.page.PageResult;
import org.example.web.vo.SysLog;

import java.util.List;
import java.util.Set;

public interface ISysUserService extends IService<SysUser> {
    int saveOrUpdateUser(SysUser user);
    int deleteBatch(List<SysUser> records);
    Set<String> findPermissions(String userName);
    List<UserRole> findUserRoles(Long userId);
    void findUserRoles(PageResult pageResult);

    void inserLog(SysLog s);
}
