package com.sww.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sww.pojo.UserInfo;

import java.sql.Wrapper;

/**
 * @author sww
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 获取用户个人信息
     * @param id 用户id
     * @return 用户个人信息
     */
    public UserInfo getUserInfo(Long id);

    /**
     * 更新用户个人信息
     * @param userInfo userInfoBean
     * @param wrapper 条件构造
     * @return 是否成功
     */
    public boolean updateUserInfo(UserInfo userInfo, UpdateWrapper<UserInfo> wrapper);
}
