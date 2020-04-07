package com.sww.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sww.pojo.UserInfo;

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
}
