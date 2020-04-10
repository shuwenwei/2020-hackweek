package com.sww.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sww.pojo.Article;
import com.sww.pojo.ArticleComment;
import com.sww.pojo.view.ViewComment;

import java.util.List;

/**
 * @author sww
 */
public interface ArticleCommentService extends IService<ArticleComment> {

    /**
     * 获取用于在推送消息中显示的单条回复
     * @param id commentId
     * @return 回复内容
     */
    public ArticleComment getCommentById(Long id);

    /**
     * 通过文章id查找评论
     * @param page page
     * @param articleId 文章id
     * @return 评论
     */
    public List<ViewComment> getCommentsByArticleId(Page<ViewComment> page, Long articleId);

    /**
     * 将文章评论保存并放入被回复者的消息列表
     * @param articleComment 文章评论
     */
    public void saveComment(ArticleComment articleComment, Article article);

}
