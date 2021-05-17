package org.example.web.controller.sys;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.common.constants.SysConstants;
import org.example.common.utils.CommonUtil;
import org.example.core.http.HttpResult;
import org.example.entity.sys.Role;
import org.example.entity.sys.RoleMenu;
import org.example.web.service.sys.IRoleService;
import org.example.web.util.ShiroUtils;
import org.example.web.util.page.PageRequest;
import org.example.web.util.page.PageUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("role")
public class SysRoleController {

	@Autowired
	private IRoleService roleService;

	@RequiresPermissions({"sys:role:add", "sys:role:edit"})
	@PostMapping(value="/save")
	public HttpResult save(@RequestBody Role record) {
		Role role = roleService.getById(record.getId());
		if(role != null) {
			if(SysConstants.ADMIN.equalsIgnoreCase(role.getName())) {
				return HttpResult.error("超级管理员不允许修改!");
			}else{
				if(!CommonUtil.isEmpty(ShiroUtils.getUser())){
					record.setUpdateBy(ShiroUtils.getUser().getUserName());
				}
				record.setUpdateTime(new Date());
			}
		}
		if((record.getId() == null || record.getId() ==0)) {
			if(roleService.getOne(new QueryWrapper<Role>().lambda()
					.eq(Role::getName,record.getName())) !=null ){
				return HttpResult.error("角色名已存在!");
			}else{
				if(!CommonUtil.isEmpty(ShiroUtils.getUser())){
					record.setUpdateBy(ShiroUtils.getUser().getUserName());
					record.setCreateBy(ShiroUtils.getUser().getUserName());
				}
				record.setUpdateTime(new Date());
				record.setCreateTime(new Date());
			}
		}
		return HttpResult.ok( roleService.saveOrUpdate(record));
	}

	@RequiresPermissions("sys:role:delete")
	@PostMapping(value="/delete")
	public HttpResult delete(@RequestBody List<Role> records) {

		List<Long> list = new LinkedList<>();
		records.forEach(x->{
			list.add(x.getId());
		});
		return HttpResult.ok(roleService.removeByIds(list));
	}

	@RequiresPermissions("sys:role:view")
	@PostMapping(value="/findPage")
	public HttpResult findPage(@RequestBody PageRequest pageRequest) {
		String name = PageUtils.getColumnFilterValue(pageRequest,
				"name");
		IPage<Role> page = new Page(pageRequest.getPageNum(),pageRequest.getPageSize());
		page = roleService.page(page,new QueryWrapper<Role>().lambda()
				.like(!CommonUtil.isBlank(name),Role::getName,name));
		return HttpResult.ok(PageUtils.getPageResult(page));
	}
	
	@RequiresPermissions("sys:role:view")
	@GetMapping(value="/findAll")
	public HttpResult findAll() {
		return HttpResult.ok(roleService.list());
	}
	
	@RequiresPermissions("sys:role:view")
	@GetMapping(value="/findRoleMenus")
	public HttpResult findRoleMenus(@RequestParam Long roleId) {
		return HttpResult.ok(roleService.findRoleMenus(roleId));
	}
	
	@RequiresPermissions("sys:role:view")
	@PostMapping(value="/saveRoleMenus")
	public HttpResult saveRoleMenus(@RequestBody List<RoleMenu> records) {
		for(RoleMenu record:records) {
			 Role sysRole = roleService.getById(record.getRoleId());
			if(SysConstants.ADMIN.equalsIgnoreCase(sysRole.getName())) {
				return HttpResult.error("超级管理员拥有所有菜单权限，不允许修改！");
			}
		}
		return HttpResult.ok(roleService.saveRoleMenus(records));
	}
}
