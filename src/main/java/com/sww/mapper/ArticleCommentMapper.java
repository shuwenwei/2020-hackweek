package com.sww.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sww.pojo.ArticleComment;
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
}
