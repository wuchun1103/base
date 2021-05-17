package org.example.web.oauth2;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import org.example.common.constants.SysConstants;
import org.example.entity.sys.SysUser;
import org.example.web.service.sys.ISysUserService;
import org.example.web.util.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 认证Realm实现
 * @author Louis
 * @date Sep 1, 2018
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

	@Autowired
    ISysUserService userService;
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(接口保护，验证接口调用权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> permsSet = (Set<String>)
                ShiroUtils.getSession().getAttribute(SysConstants.SESSIONKEYRIGHT);
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        OAuth2Token oAuth2Token = (OAuth2Token) authenticationToken;
        String token = (String) oAuth2Token.getPrincipal();
        String loginName = oAuth2Token.getUserCode();

        // 查询用户信息
        SysUser user = userService.getOne(new QueryWrapper<SysUser>().lambda()
        .eq(SysUser::getLoginName,loginName));
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, token, getName());
        ShiroUtils.setSessionAttribute(SysConstants.SESSIONKEY,user);

        Set<String> permsSet = userService.findPermissions(user.getLoginName());
        ShiroUtils.setSessionAttribute(SysConstants.SESSIONKEYRIGHT,permsSet);
        return info;
    }
}
