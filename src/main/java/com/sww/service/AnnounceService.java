package com.sww.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sww.pojo.Announce;
import com.sww.pojo.view.ViewAnnounce;

/**
 * @author sww
 */
public interface AnnounceService extends IService<Announce> {

    /**
     * 获取通知
     * @param page page
     * @param userId 用户id
     * @return viewAnnounce列表
     */
    public IPage<ViewAnnounce> getViewAnnounceList(Page<ViewAnnounce> page, Long userId);
}
