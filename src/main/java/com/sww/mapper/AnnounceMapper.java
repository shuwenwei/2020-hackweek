package com.sww.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sww.pojo.Announce;
import com.sww.pojo.view.ViewAnnounce;

/**
 * @author sww
 */
public interface AnnounceMapper extends BaseMapper<Announce> {

    /**
     * 获取通知
     * @param page page
     * @param userId 用户id
     * @return viewAnnounce列表
     */
    public IPage<ViewAnnounce> getViewAnnounceList(Page<ViewAnnounce> page, Long userId);
}
