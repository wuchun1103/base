package org.example.web.service.sys.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.sys.RoleMenu;
import org.example.web.mapper.sys.RoleMenuMapper;
import org.example.web.service.sys.IRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

}
