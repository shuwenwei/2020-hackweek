package com.sww.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.UserMapper;
import com.sww.pojo.User;
import com.sww.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author sww
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private UserMapper userMapper;

    @Resource
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper
                .selectOne(new QueryWrapper<User>()
                        .eq("username", username));
    }

    @Override
    public boolean checkUsername(String username) {
        return (findUserByUsername(username) != null);
    }

    @Override
    public boolean checkEmail(String email) {
        return userMapper
                .selectOne(new QueryWrapper<User>()
                        .eq("email", email)) != null;
    }

}
