package com.sww.config.shiroconfig;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sww.pojo.User;
import com.sww.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sww
 */
public class MyRealm extends AuthorizingRealm {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = principalCollection.toString();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        User user = userService.getOne(userQueryWrapper.eq("username", username));
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(user.getRole());
        return info;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getCredentials();
        return new SimpleAuthenticationInfo(username, username, "myRealm");
    }
}
