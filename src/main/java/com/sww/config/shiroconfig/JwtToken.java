package com.sww.config.shiroconfig;

import com.sww.pojo.User;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author sww
 */
public class JwtToken implements AuthenticationToken {

    private User user;

    public JwtToken(User user) {
        this.user = user;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public Object getCredentials() {
        return user;
    }
}