package com.sww.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.SwankCommentMapper;
import com.sww.pojo.SwankComment;
import com.sww.service.SwankCommentService;
import org.springframework.stereotype.Service;

/**
 * @author sww
 */
@Service
public class SwankCommentServiceImpl extends ServiceImpl<SwankCommentMapper, SwankComment> implements SwankCommentService {
}
