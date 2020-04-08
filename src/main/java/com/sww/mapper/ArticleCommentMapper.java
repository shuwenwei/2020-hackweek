package com.sww.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sww.pojo.ArticleComment;
import com.sww.pojo.view.ViewComment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author sww
 */
@Mapper
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {
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
    public IPage<ViewComment> getCommentsByArticleId(Page<ViewComment> page, Long articleId);
}
