package com.sww.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.StoryMapper;
import com.sww.pojo.Story;
import org.springframework.stereotype.Service;

@Service
public class StoryServiceImpl extends ServiceImpl<StoryMapper, Story> implements StoryService {
}
