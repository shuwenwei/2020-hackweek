package com.sww.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.StoryMapper;
import com.sww.pojo.Story;
import com.sww.service.StoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sww
 */
@Service
public class StoryServiceImpl extends ServiceImpl<StoryMapper, Story> implements StoryService {

    @Override
    public List<Story> getStoryList(Integer page) {
        return page(new Page<Story>().setCurrent(page))
                .getRecords();
    }

    @Override
    public boolean storyExist(Long storyId) {
        return null != getOne(new QueryWrapper<Story>().eq("id", storyId));
    }
}