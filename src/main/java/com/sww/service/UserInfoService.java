package com.sww.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sww.pojo.UserInfo;
import com.sww.pojo.view.ViewListUser;
import com.sww.pojo.view.ViewUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author sww
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 获取用户个人信息
     * @param id 用户id
     * @return 用户个人信息
     */
    public ViewUserInfo getUserInfo(Long id);

    /**
     * 更新用户个人信息
     * @param userInfo userInfoBean
     * @param wrapper 条件构造
     * @return 是否成功
     */
    public boolean updateUserInfo(UserInfo userInfo, UpdateWrapper<UserInfo> wrapper);

    /**
     * 获取粉丝信息
     * @param usersId 粉丝的id集合
     * @return ViewFollower列表
     */
    public List<ViewListUser> getViewListUsers(@Param("set") Set usersId);
}
