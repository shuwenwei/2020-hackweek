package com.sww.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.SwankMapper;
import com.sww.pojo.Swank;
import com.sww.service.SwankService;
import org.springframework.stereotype.Service;

/**
 * @author sww
 */
@Service
public class SwankServiceImpl extends ServiceImpl<SwankMapper, Swank> implements SwankService {

    @Override
    public boolean swankExist(Long swankId) {
        return null != getOne(new QueryWrapper<Swank>().eq("id", swankId));
    }
}
