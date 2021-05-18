package org.example.web.service.sys;


import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.sys.Menu;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *

 */
public interface IMenuService extends IService<Menu> {

    int saveOrUpdateMenu(Menu record);

    List<Menu> findTree(String userName, int menuType);

    List<Menu> findByUser(String userName);

}
