package com.sww.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.AnnounceMapper;
import com.sww.mapper.ArticleCommentMapper;
import com.sww.pojo.Announce;
import com.sww.pojo.Article;
import com.sww.pojo.ArticleComment;
import com.sww.pojo.view.ViewComment;
import com.sww.service.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sww
 */
@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment> implements ArticleCommentService {

    private ArticleCommentMapper articleCommentMapper;
    private AnnounceMapper announceMapper;

    @Autowired
    public void setAnnounceMapper(AnnounceMapper announceMapper) {
        this.announceMapper = announceMapper;
    }

    @Resource
    public void setArticleCommentMapper(ArticleCommentMapper articleCommentMapper) {
        this.articleCommentMapper = articleCommentMapper;
    }

    @Override
    public ArticleComment getCommentById(Long id) {
        return articleCommentMapper.getCommentById(id);
    }

    @Override
    public List<ViewComment> getCommentsByArticleId(Page<ViewComment> page, Long articleId) {
        return articleCommentMapper
                .getCommentsByArticleId(page, articleId)
                .getRecords();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveComment(ArticleComment articleComment, Article article) {
        save(articleComment);
        //文章作者和回复作者相同则不保存通知
        if (article.getAuthorId().equals(articleComment.getAuthorId())) {
            return;
        }
        Announce announce = new Announce(
                article.getAuthorId()
                , "articleReply"
                , article.getId()
                , articleComment.getContent()
                , articleComment.getAuthorId());

        announceMapper.insert(announce);
    }
}
