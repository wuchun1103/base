package org.example.web.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.common.enums.sys.EnumDeleteFlag;
import org.example.core.http.HttpResult;
import org.example.entity.sys.SysUser;
import org.example.entity.sys.UserToken;
import org.example.web.oauth2.OAuth2Token;
import org.example.web.service.sys.ISysUserService;
import org.example.web.util.ShiroUtils;
import org.example.web.util.TokenGenerator;
import org.example.web.vo.LoginBean;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

/**
 * 登录控制器
 */
@RestController
@RequestMapping("/login")
public class SysLoginController {

	@Autowired
	private ISysUserService userService;

	/**
	 * 登录接口
	 */
	@PostMapping(value = "/login")
	public HttpResult login(@RequestBody LoginBean loginBean) throws IOException {
		String loginName = loginBean.getAccount();
		String password = loginBean.getPassword();
		 SysUser user = userService.getOne(new QueryWrapper<SysUser>().lambda()
		 .eq(SysUser::getLoginName,loginName)
		 .eq(SysUser::getDelFlag, EnumDeleteFlag.DELTET_FLAG_YES.getIndex()));
		if (user == null) {
			return HttpResult.error("账号不存在");
		}
		if (!matchPassword(user, password)) {
			return HttpResult.error("密码不正确");
		}
		 UserToken data = new UserToken();
		data.setToken(TokenGenerator.generateToken());
		data.setUserId(user.getId());
		data.setLoginName(user.getLoginName());
		data.setUserName(user.getUserName());
		data.setCreateTime(new Date());
		Subject subject =  ShiroUtils.getSubject();
        subject.login(new OAuth2Token(data.getToken(),user.getLoginName()));
		return HttpResult.ok(data);
	}

	/**
	 * 验证用户密码
	 * @param user
	 * @param password
	 * @return
	 */
	public boolean matchPassword(SysUser user, String password) {
		return user.getPassWord().equals( password );
	}
	
	/**
	 * 登出接口
	 */
	@GetMapping(value = "/logout")
	public HttpResult logout() {
		ShiroUtils.logout();
		return HttpResult.ok();
	}

}
