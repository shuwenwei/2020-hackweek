package com.sww.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.UserInfoMapper;
import com.sww.pojo.UserInfo;
import com.sww.service.UserInfoService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author sww
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    @Cacheable(value = "userInfo", key = "#id")
    public UserInfo getUserInfo(Long id) {
        return baseMapper
                .selectOne(new QueryWrapper<UserInfo>()
                        .eq("user_id", id));
    }

}
