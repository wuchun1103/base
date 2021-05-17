package org.example.web.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 自定义 token 对象
 * @author Louis
 * @date Sep 1, 2018
 */
public class OAuth2Token implements AuthenticationToken {
	private static final long serialVersionUID = 1L;
	
	private String token;
	private String userCode;

    public OAuth2Token(String token){
        this.token = token;
    }
    public OAuth2Token(String token,String userCode){
        this.token = token;
        this.userCode = userCode;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public String getUserCode(){return userCode;}
}
