package org.example.web.mapper.sys;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.entity.sys.Menu;
import org.apache.ibatis.annotations.Param;
import org.example.web.vo.SysLog;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> findByUserName(@Param(value = "loginName") String loginName);
    List<Menu> findRoleMenus(@Param(value = "roleId") Long roleId);


    void insertSysLog(SysLog s);
}
