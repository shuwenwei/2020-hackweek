package com.sww.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.ArticleCommentMapper;
import com.sww.pojo.ArticleComment;
import com.sww.service.ArticleCommentService;
import org.springframework.stereotype.Service;

/**
 * @author sww
 */
@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment> implements ArticleCommentService {
}
