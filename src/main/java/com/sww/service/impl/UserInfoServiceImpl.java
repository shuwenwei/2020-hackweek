package com.sww.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import com.sww.mapper.UserInfoMapper;
import com.sww.pojo.UserInfo;
import com.sww.service.UserInfoService;
import com.sww.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author sww
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    private RedisUtil redisUtil;
    private static String USER_INFO_PREFIX = "userInfo::";

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public UserInfo getUserInfo(Long id) {
        String key = USER_INFO_PREFIX + id.toString();
        UserInfo cacheUserInfo = (UserInfo) redisUtil.get(key);
        //如果缓存中不存在userInfo
        if (cacheUserInfo != null) {
            return cacheUserInfo;
        }

        UserInfo userInfo = baseMapper
                .selectOne(new QueryWrapper<UserInfo>()
                        .eq("user_id", id));
        redisUtil.set(key, userInfo);
        return userInfo;
    }

    @Override
    public boolean updateUserInfo(UserInfo userInfo, UpdateWrapper<UserInfo> wrapper) {
        String key = USER_INFO_PREFIX + userInfo.getUserId().toString();

        boolean update = update(userInfo, wrapper);

        if (update && redisUtil.hasKey(key)) {
            UserInfo cacheUserInfo = (UserInfo) redisUtil.get(key);
            cacheUserInfo.setBirth(userInfo.getBirth());
            cacheUserInfo.setIntroduction(userInfo.getIntroduction());
            redisUtil.set(key, cacheUserInfo);
        }

        return update;
    }

}
