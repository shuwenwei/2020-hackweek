package com.sww.config.shiroconfig;

import com.alibaba.fastjson.JSON;
import com.sww.pojo.ResponseBean;
import com.sww.pojo.User;
import com.sww.util.JwtUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author sww
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        return true;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("Authorization");
        if (token != null && !"".equals(token)) {
            User user = JwtUtil.getUser(token);
            if (user != null) {
//                System.out.println(user.getUsername());
                System.out.println(user.getRole());
                getSubject(request, response).login(new JwtToken(user));
                return super.isAccessAllowed(request, response, mappedValue);
            }
//            String username = user.getUsername();
//            if (username != null) {
//                getSubject(request, response).login(new JwtToken(username));
//                return super.isAccessAllowed(request, response, mappedValue);
//            }
            String message = "ineffective token";
            loginFailed(response, message);
            return false;
        }
        String message = "require login";
        loginFailed(response, message);
        return false;
    }

    private void loginFailed(ServletResponse response, String msg) {
        String json = generateJson(msg);
        response.setContentType("application/json");
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateJson(String msg) {
        ResponseBean bean = new ResponseBean(msg, null, 0);
        return JSON.toJSONString(bean);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        return false;
    }

}
