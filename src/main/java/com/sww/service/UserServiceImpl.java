package com.sww.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.UserMapper;
import com.sww.pojo.User;
import org.springframework.stereotype.Service;

/**
 * @author sww
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
