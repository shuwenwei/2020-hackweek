package com.sww.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sww.pojo.Swank;

/**
 * @author sww
 */
public interface SwankService extends IService<Swank> {
    /**
     * 判断swank是否存在
     * @param swankId swank id
     * @return true 存在 false 不存在
     */
    public boolean swankExist(Long swankId);
}
