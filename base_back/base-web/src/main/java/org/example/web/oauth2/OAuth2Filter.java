package org.example.web.oauth2;


import com.alibaba.fastjson.JSONObject;
import org.example.common.utils.CommonUtil;
import org.example.core.http.HttpResult;
import org.example.core.http.HttpStatus;
import org.example.entity.sys.SysUser;
import org.example.web.util.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Oauth2过滤器
 * @date Sep 1, 2018
 */
public class OAuth2Filter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        // 获取请求token
        String token = getRequestToken((HttpServletRequest) request);
        if(CommonUtil.isBlank(token)){
            return null;
        }
        return new OAuth2Token(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws   Exception   {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if("OPTIONS".equals(httpRequest.getMethod())) {
            // 如果是跨域中复杂请求的预检请求（OPTIONS类型），
            // 因为预检请求不带token, 所以不需要验证token
            return true;
        }
        // 获取请求token，如果token不存在，直接返回401
        String token = getRequestToken(httpRequest);
        if(CommonUtil.isBlank(token)){
            setResponse(HttpStatus.SC_UNAUTHORIZED, "invalid token",response);
            return false;
        }
         SysUser curUser = ShiroUtils.getUser();
        if( curUser== null  ) {
            setResponse(HttpStatus.SC_UNAUTHORIZED, "invalid token",response);
            return false;
        }
        if(SecurityUtils.getSubject().getPrincipal()==null) {//本地内存已无
            Subject subject =  ShiroUtils.getSubject();
            subject.login(new OAuth2Token(token,curUser.getLoginName()));
        }
        return true;
    }
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json; charset=utf-8");
        try {
            // 处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            HttpResult result = HttpResult.error(HttpStatus.SC_UNAUTHORIZED,
                    throwable.getMessage());
            String json = JSONObject.toJSONString(result);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
        }
        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        // 从header中获取token
        String token = httpRequest.getHeader("token");
        // 如果header中不存在token，则从参数中获取token
        if(CommonUtil.isBlank(token)){
            token = httpRequest.getParameter("token");
        }
        return token;
    }

    //处理返回的异常
    private void setResponse(int status,String errStr,ServletResponse response) throws IOException{
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setStatus(5000);
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(errStr);
        out.flush();
        out.close();
    }

}

