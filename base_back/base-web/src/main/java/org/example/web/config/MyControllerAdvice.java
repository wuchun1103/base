package org.example.web.config;

import org.example.core.exception.KfmException;
import org.example.core.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
@Slf4j
public class MyControllerAdvice  {


    /**
     *捕获无权限异常
     */
    @ResponseBody
    @ExceptionHandler({UnauthorizedException.class , AuthorizationException.class ,
            LockedAccountException.class})
    public void authorizationException(HttpServletResponse resp,Exception e)
            throws IOException, ServletException {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED );
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        //out.write(e.getMessage());
        out.write("权限不足，请联系管理员");
        out.flush();
        out.close();
    }

    /**
     *捕获通用异常
     */
    @ResponseBody
    @ExceptionHandler({KfmException.class , Exception.class})
    public void commonException(HttpServletResponse resp ,Exception e)
            throws IOException {
        resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(e.getMessage());
        out.flush();
        out.close();

        e.printStackTrace();
    }
}
