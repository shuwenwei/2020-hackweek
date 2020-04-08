package com.sww.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.InnerCommentMapper;
import com.sww.pojo.InnerComment;
import com.sww.service.InnerCommentService;
import org.springframework.stereotype.Service;

/**
 * @author sww
 */
@Service
public class InnerCommentServiceImpl extends ServiceImpl<InnerCommentMapper, InnerComment> implements InnerCommentService {
}
