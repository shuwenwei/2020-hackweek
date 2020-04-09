package com.sww.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sww.pojo.UserInfo;
import com.sww.pojo.view.ViewListUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author sww
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    /**
     * 获取粉丝信息
     * @param usersId 粉丝的id集合
     * @return ViewFollower列表
     */
    public List<ViewListUser> getViewListUsers(@Param("set") Set usersId);
}
