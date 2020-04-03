package com.sww.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sww.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author sww
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
