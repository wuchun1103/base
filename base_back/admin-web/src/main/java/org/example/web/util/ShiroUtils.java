package org.example.web.util;

import org.example.common.constants.SysConstants;
import org.example.entity.sys.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro相关工具类
 * @date Oct 29, 2018
 */
public class ShiroUtils {

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static SysUser getUser() {
		Object user = SecurityUtils.getSubject().getPrincipal();
		if(user==null) {
			System.out.println("当前shiro会话没有该用户对象的sessionid========"
					+getSession().getId());
			user = getSession().getAttribute(SysConstants.SESSIONKEY);
		}
		if(user==null) {
			System.out.println("redis也取不到的sessionid========"
					+getSession().getId());
		}
		return  (SysUser) user;
	}

	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}



	public static void logout() {
		SecurityUtils.getSubject().logout();
	}

	
}
