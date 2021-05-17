package org.example.web.controller.sys;


import org.example.core.http.HttpResult;
import org.example.entity.sys.Menu;
import org.example.web.service.sys.IMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * 菜单控制器
 */
@RestController
@RequestMapping("menu")
public class SysMenuController {

	@Autowired
	private IMenuService menuService;
	
	@RequiresPermissions({"sys:menu:add", "sys:menu:edit"})
	@PostMapping(value="/save")
	public HttpResult save(@RequestBody Menu record) {
		return HttpResult.ok(menuService.saveOrUpdateMenu(record));
	}

	@RequiresPermissions("sys:menu:delete")
	@PostMapping(value="/delete")
	public HttpResult delete(@RequestBody List<Menu> records) {
		List<Long> list = new LinkedList<Long>();
		records.stream().forEach(x->{list.add(x.getId());});
		return HttpResult.ok(menuService.removeByIds(list));
	}

	@GetMapping(value="/findNavTree")
	public HttpResult findNavTree(@RequestParam String userName) {
		return HttpResult.ok(menuService.findTree(userName, 1));
	}

	@GetMapping(value="/findMenuTree")
	public HttpResult findMenuTree() {
		return HttpResult.ok(menuService.findTree(null, 0));
	}
}
