package com.sww.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.ArticleCommentMapper;
import com.sww.pojo.ArticleComment;
import com.sww.service.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author sww
 */
@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment> implements ArticleCommentService {

    private ArticleCommentMapper articleCommentMapper;

    @Resource
    public void setArticleCommentMapper(ArticleCommentMapper articleCommentMapper) {
        this.articleCommentMapper = articleCommentMapper;
    }

    @Override
    public ArticleComment getCommentById(Long id) {
        return articleCommentMapper.getCommentById(id);
    }
}
