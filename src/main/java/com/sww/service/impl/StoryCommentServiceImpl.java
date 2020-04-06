package com.sww.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.StoryCommentMapper;
import com.sww.pojo.StoryComment;
import com.sww.service.StoryCommentService;
import org.springframework.stereotype.Service;

/**
 * @author sww
 */
@Service
public class StoryCommentServiceImpl extends ServiceImpl<StoryCommentMapper, StoryComment> implements StoryCommentService {
}
