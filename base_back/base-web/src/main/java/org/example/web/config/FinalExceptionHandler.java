package org.example.web.config;

import org.example.common.constants.SysConstants;
import org.example.core.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@RestController
public class FinalExceptionHandler  implements ErrorController {


    private ErrorAttributes errorAttributes;

    @Autowired
    public FinalExceptionHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes=errorAttributes;
    }


    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error")
    @ResponseBody
    public void error(HttpServletRequest request, HttpServletResponse resp)
            throws IOException {

        ServletWebRequest requestAttributes = new ServletWebRequest(request);
        Map<String, Object> attr =
                this.errorAttributes.getErrorAttributes(requestAttributes,
                        false);

//        System.out.println(attr);
        int status = (int)attr.get("status");
        String message = (String)attr.get("message");
//        System.out.println("messsage====>"+message);
        // 错误处理逻辑
        if(SysConstants.TokenExpire.equals(message)) {
            resp.setStatus(HttpStatus.LOG_OUT_CODE);//5000前台写死为重新登录
        } else {
            resp.setStatus(status);
        }
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(message);
        out.flush();
        out.close();
    }
}

