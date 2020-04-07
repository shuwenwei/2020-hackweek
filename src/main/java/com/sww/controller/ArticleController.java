package com.sww.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sww.exception.BadRequestException;
import com.sww.pojo.*;
import com.sww.pojo.view.PackedArticle;
import com.sww.service.*;
import com.sww.util.BindingResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.List;


/**
 * @author sww
 */
@RestController
@RequiresRoles("user")
@RequestMapping("/article")
public class ArticleController {

    private ArticleService articleService;
    private ArticleCommentService articleCommentService;

    @Autowired
    public void setArticleCommentService(ArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/swank")
    public ResponseBean postSwank(@RequestBody @Validated Article article
            , BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        saveArticle(article, 0);
        return new ResponseBean("发布成功", null, 1);
    }

    @PostMapping("/story")
    public ResponseBean postStory(@RequestBody @Validated Article article
            , BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        saveArticle(article, 1);
        return new ResponseBean("发布成功", null, 1);
    }

    @GetMapping("/swank")
    public ResponseBean getSwanks(@RequestParam @Min(value = 1) int page, BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        List<PackedArticle> swanks = articleService.getArticles(page, 0);
        return new ResponseBean("获取成功", swanks, 1);
    }

    @GetMapping("/story")
    public ResponseBean getStories(@RequestParam @Min(value = 1) int page, BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        List<PackedArticle> stories = articleService.getArticles(page, 1);
        return new ResponseBean("获取成功", stories, 1);
    }

    @PostMapping("/comment")
    public ResponseBean postComment(@RequestBody @Validated ArticleComment articleComment, BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        if (articleService.articleExist(articleComment.getToArticle())) {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            Long userId = user.getId();
            articleComment.setAuthorId(userId);
            articleCommentService.save(articleComment);

            sendMessageToWebsocket(articleComment);

            return new ResponseBean("发布成功", null, 1);
        }
        throw new BadRequestException("文章不存在");
    }

    private void saveArticle(Article article, int type) {
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        article.setAuthorId(currentUser.getId());
        article.setArticleType(type);
        articleService.save(article);
    }

    private void sendMessageToWebsocket(ArticleComment articleComment) {
        String articleAuthorId = articleComment.getAuthorId().toString();
        WebSocketResponseBean bean = new WebSocketResponseBean("comment", "收到一条新回复", articleComment.getContent());
        //给文章作者发送
        try {
            WebSocketService.sendMessage(articleAuthorId, bean);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }

        Long toComment = articleComment.getToComment();
        if (toComment == null) {
            return;
        }
        //给该条评论的作者推送
        String toCommentAuthorId = articleCommentService
                .getOne(new QueryWrapper<ArticleComment>()
                        .eq("id", toComment))
                .getAuthorId()
                .toString();
        try {
            WebSocketService.sendMessage(toCommentAuthorId, bean);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }
}
