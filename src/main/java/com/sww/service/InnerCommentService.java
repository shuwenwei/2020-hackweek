package com.sww.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sww.pojo.ArticleComment;
import com.sww.pojo.InnerComment;

/**
 * @author sww
 */
public interface InnerCommentService extends IService<InnerComment> {

    /**
     * 保存楼中楼评论
     * @param innerComment 楼中楼评论
     * @param articleComment 文章评论
     */
    public void saveInnerComment(InnerComment innerComment, ArticleComment articleComment);
}
