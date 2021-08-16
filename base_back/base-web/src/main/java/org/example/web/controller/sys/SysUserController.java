package org.example.web.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.example.common.constants.SysConstants;
import org.example.common.utils.CommonUtil;
import org.example.core.http.HttpResult;
import org.example.entity.sys.SysUser;
import org.example.web.annotation.sysLog;
import org.example.web.service.sys.ISysUserService;
import org.example.web.util.ShiroUtils;
import org.example.web.util.page.PageRequest;
import org.example.web.util.page.PageResult;
import org.example.web.util.page.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("user")
public class SysUserController {

    @Autowired
    private ISysUserService userService;

    @GetMapping(value="/findPermissions")
    public HttpResult findPermissions(@RequestParam String name) {
        return HttpResult.ok(userService.findPermissions(name));
    }


    @RequiresPermissions({"sys:user:add", "sys:user:edit"})
    @PostMapping(value="/save")
    @sysLog
    public HttpResult save(@RequestBody SysUser record) {
        SysUser user =userService.getById(record.getId());
        if(user != null) {
            if(SysConstants.ADMIN.equalsIgnoreCase(user.getLoginName())) {
                return HttpResult.error("超级管理员不允许修改!");
            }
        }
        if(record.getPassWord() != null) {
            if(!record.getPassWord().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$")){
               return HttpResult.error("密码须包含数字、字母两种元素，且密码位数为6-16位");
            }
            if(user == null) {
                // 新增用户
                if(userService.getOne( new QueryWrapper<SysUser>().lambda()
                        .eq(SysUser::getLoginName,record.getLoginName()))
                        != null) {
                    return HttpResult.error("用户名已存在!");
                }else{
                    if(!CommonUtil.isEmpty(ShiroUtils.getUser())){
                        record.setCreateBy(ShiroUtils.getUser().getUserName());
                        record.setUpdateBy(ShiroUtils.getUser().getUserName());
                    }
                    record.setCreateTime(new Date());
                    record.setUpdateTime(new Date());
                }
            }else{
                if(!CommonUtil.isEmpty(ShiroUtils.getUser())){
                    record.setUpdateBy(ShiroUtils.getUser().getUserName());
                }
                record.setUpdateTime(new Date());
            }
        }
        return HttpResult.ok(userService.saveOrUpdateUser(record));
    }

    @RequiresPermissions("sys:user:delete")
    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<SysUser> records) {
        for(SysUser record:records) {
            SysUser sysUser = userService.getById(record.getId());
            if(sysUser != null && SysConstants.ADMIN.equalsIgnoreCase(
                    sysUser.getLoginName())) {
                return HttpResult.error("超级管理员不允许删除!");
            }
        }
        return HttpResult.ok(userService.deleteBatch(records));
    }


    @RequiresPermissions("sys:user:view")
    @GetMapping(value="/findUserRoles")
    public HttpResult findUserRoles(@RequestParam Long userId) {
        return HttpResult.ok(userService.findUserRoles(userId));
    }

    @RequiresPermissions("sys:user:view")
    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {

        String loginName = PageUtils.getColumnFilterValue(pageRequest,
                "loginName");
        String userName = PageUtils.getColumnFilterValue(pageRequest,
                "userName");
        IPage<SysUser> page = new Page(pageRequest.getPageNum(),pageRequest.getPageSize());
        page = userService.page(page,new QueryWrapper<SysUser>().lambda()
                .like(!CommonUtil.isBlank(loginName), SysUser::getLoginName,loginName)
                .like(!CommonUtil.isBlank(userName), SysUser::getUserName,userName));
        PageResult pres = PageUtils.getPageResult(page);
        userService.findUserRoles(pres);
        return HttpResult.ok(pres);
    }

    /**
     * 修改登录用户密码
     */
    @RequiresPermissions("sys:user:edit")
    @GetMapping("/updatePassword")
    public HttpResult updatePassword(@RequestParam String password,
                                     @RequestParam String newPassword) {
        SysUser user = ShiroUtils.getUser();

        if(user != null && password != null && newPassword != null) {
            if(SysConstants.ADMIN.equalsIgnoreCase(user.getLoginName())) {
                return HttpResult.error("超级管理员不允许修改!");
            }

            if(!user.getPassWord().equals(password)) {
                return HttpResult.error("原密码不正确");
            }
            if(!newPassword.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$")){
                return HttpResult.error("新密码须包含数字、字母两种元素，且密码位数为6-16位");
            }
            user.setPassWord(newPassword);
            userService.update(new SysUser()
                            .setPassWord(newPassword)
                            .setUpdateTime(new Date())
                            .setUpdateBy(!CommonUtil.isEmpty(ShiroUtils.getUser())?ShiroUtils.getUser().getUserName():""),
                    new UpdateWrapper<SysUser>().lambda()
                            .eq(SysUser::getLoginName,user.getLoginName()));
            return   HttpResult.ok();
        }
        return HttpResult.error();
    }

    //	@RequiresPermissions("sys:user:view")
    @GetMapping(value="/findByName")
    public HttpResult findByDocCode(@RequestParam String name) {
        return HttpResult.ok(userService.getOne(new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getLoginName,name)));
    }


    @GetMapping(value="/testGet")
    public String testGet() {
       return "测试成功";
    }

}
