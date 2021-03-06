package com.sww.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.UserInfoMapper;
import com.sww.pojo.UserInfo;
import com.sww.pojo.view.ViewListUser;
import com.sww.pojo.view.ViewUserInfo;
import com.sww.service.UserInfoService;
import com.sww.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author sww
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    private RedisUtil redisUtil;
    private UserInfoMapper userInfoMapper;
    private static String USER_INFO_PREFIX = "userInfo::";

    @Autowired
    public void setUserInfoMapper(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public ViewUserInfo getUserInfo(Long id) {
        String key = USER_INFO_PREFIX + id.toString();
        ViewUserInfo cacheUserInfo = (ViewUserInfo) redisUtil.get(key);
        //如果缓存中不存在userInfo
        if (cacheUserInfo != null) {
            return cacheUserInfo;
        }

        ViewUserInfo userInfo = userInfoMapper.getViewUserInfo(id);
        redisUtil.set(key, userInfo);
        return userInfo;
    }

    @Override
    public boolean updateUserInfo(UserInfo userInfo, UpdateWrapper<UserInfo> wrapper) {
        String key = USER_INFO_PREFIX + userInfo.getUserId().toString();

        boolean update = update(userInfo, wrapper);

        if (update && redisUtil.hasKey(key)) {
            ViewUserInfo cacheUserInfo = (ViewUserInfo) redisUtil.get(key);
            cacheUserInfo.setBirth(userInfo.getBirth());
            cacheUserInfo.setIntroduction(userInfo.getIntroduction());
            redisUtil.set(key, cacheUserInfo);
        }

        return update;
    }

    @Override
    public List<ViewListUser> getViewListUsers(Set<Object> usersId) {
        return userInfoMapper.getViewListUsers(usersId);
    }

}
