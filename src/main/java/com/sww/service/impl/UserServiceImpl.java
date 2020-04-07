package com.sww.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.UserInfoMapper;
import com.sww.mapper.UserMapper;
import com.sww.pojo.User;
import com.sww.pojo.UserInfo;
import com.sww.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Resource;

/**
 * @author sww
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private UserInfoMapper userInfoMapper;

    @Autowired
    public void setUserInfoMapper(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public User findUserByUsername(String username) {
        return baseMapper
                .selectOne(new QueryWrapper<User>()
                        .eq("username", username));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean addUser(User user) {
        save(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfoMapper.insert(userInfo);
        return false;
    }

    @Override
    public boolean checkUsername(String username) {
        return (findUserByUsername(username) != null);
    }

    @Override
    public boolean checkEmail(String email) {
        return baseMapper
                .selectOne(new QueryWrapper<User>()
                        .eq("email", email)) != null;
    }

}
