package com.sww.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sww.pojo.Story;

import java.util.List;

/**
 * @author sww
 */
public interface StoryService extends IService<Story> {

    /**
     * 通过页数查找story
     * @param page 页数
     * @return story list
     */
    public List<Story> getStoryList(Integer page);

    /**
     * 判断story是否存在
     * @param storyId story id
     * @return true 存在 false 不存在
     */
    public boolean storyExist(Long storyId);
}
