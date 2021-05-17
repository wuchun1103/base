package org.example.web.service.sys;


import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.sys.Menu;
import org.example.entity.sys.Role;
import org.example.entity.sys.RoleMenu;

import java.util.List;

public interface IRoleService extends IService<Role> {

    List<Menu> findRoleMenus(Long roleId);

    int saveRoleMenus(List<RoleMenu> records);
}
