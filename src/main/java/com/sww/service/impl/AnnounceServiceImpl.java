package com.sww.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.AnnounceMapper;
import com.sww.pojo.Announce;
import com.sww.pojo.view.ViewAnnounce;
import com.sww.service.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sww
 */
@Service
public class AnnounceServiceImpl extends ServiceImpl<AnnounceMapper, Announce> implements AnnounceService {

    private AnnounceMapper announceMapper;

    @Autowired
    public void setAnnounceMapper(AnnounceMapper announceMapper) {
        this.announceMapper = announceMapper;
    }

    @Override
    public IPage<ViewAnnounce> getViewAnnounceList(Page<ViewAnnounce> page, Long userId) {
        return announceMapper.getViewAnnounceList(page, userId);
    }
}
