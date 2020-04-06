package com.sww.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sww.pojo.ArticleComment;

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
}
