package org.example.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.web.annotation.sysLog;
import org.example.web.service.sys.ISysUserService;
import org.example.web.vo.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Slf4j
@Component
public class sysLogAspect {

    @Autowired
    private ISysUserService userService;

    @AfterReturning(value = "@annotation(org.example.web.annotation.sysLog)")
    public void after(JoinPoint joinPoint) throws Throwable{
        Class clazz = joinPoint.getTarget().getClass();String methodName = joinPoint.getSignature().getName();
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        Method methdo = clazz.getMethod(methodName, parameterTypes);
        SysLog s = new SysLog();
        s.setName(methodName);
        if(methdo.getAnnotation(sysLog.class) !=null){
            sysLog ss = methdo.getAnnotation(sysLog.class);
            Integer s2 = ss.type();
            s.setType(s2);
        }
        s.setCreateTime(new Date());
        s.setUpdateTime(new Date());
        userService.inserLog(s);

    }
}
