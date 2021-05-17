package org.example.web.service.sys.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.sys.UserRole;
import org.example.web.mapper.sys.UserRoleMapper;
import org.example.web.service.sys.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
