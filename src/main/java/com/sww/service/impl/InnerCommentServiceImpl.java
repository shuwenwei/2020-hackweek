package com.sww.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.AnnounceMapper;
import com.sww.mapper.InnerCommentMapper;
import com.sww.pojo.Announce;
import com.sww.pojo.ArticleComment;
import com.sww.pojo.InnerComment;
import com.sww.service.InnerCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sww
 */
@Service
public class InnerCommentServiceImpl extends ServiceImpl<InnerCommentMapper, InnerComment> implements InnerCommentService {

    private AnnounceMapper announceMapper;

    @Autowired
    public void setAnnounceMapper(AnnounceMapper announceMapper) {
        this.announceMapper = announceMapper;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveInnerComment(InnerComment innerComment, ArticleComment comment) {
        save(innerComment);
        Announce announce = new Announce(
                comment.getAuthorId()
                , "commentReply"
                , comment.getToArticle()
                , innerComment.getContent()
                , innerComment.getUserId());
        announceMapper.insert(announce);
    }
}
