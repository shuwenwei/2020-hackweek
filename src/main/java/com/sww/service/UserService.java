package com.sww.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sww.pojo.User;

/**
 * @author sww
 */
public interface UserService extends IService<User> {

    /**
     * find user
     * @param username username
     * @return user
     */
    public User findUserByUsername(String username);

    /**
     * 添加一个用户
     * @param user 用户对象
     * @return 是否成功
     */
    public boolean addUser(User user);

    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return 是否可用
     */
    public boolean checkUsername(String username);


    /**
     * 检查邮箱是否存在
     * @param email 用户邮箱
     * @return 邮箱是否存在
     */
    public boolean checkEmail(String email);
}
