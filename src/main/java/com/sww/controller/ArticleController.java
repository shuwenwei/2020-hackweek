package com.sww.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sww.exception.BadRequestException;
import com.sww.pojo.*;
import com.sww.pojo.view.PackedArticle;
import com.sww.pojo.view.ViewComment;
import com.sww.service.*;
import com.sww.util.BindingResultUtil;
import com.sww.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


/**
 * @author sww
 */
@RestController
@RequiresRoles("user")
@RequestMapping("/article")
public class ArticleController {

    private ArticleService articleService;
    private ArticleCommentService articleCommentService;
    private InnerCommentService innerCommentService;
    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Autowired
    public void setInnerCommentService(InnerCommentService innerCommentService) {
        this.innerCommentService = innerCommentService;
    }

    @Autowired
    public void setArticleCommentService(ArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/like/{articleId}")
    public ResponseBean likeArticle(@PathVariable Long articleId) {
        if (!articleService.articleExist(articleId)) {
            throw new BadRequestException("文章不存在");
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //m
        Long userId = user.getId();
        boolean liked = redisUtil.isLiked(userId, articleId);
        if (liked) {
            redisUtil.unlike(userId, articleId);
            return new ResponseBean("取消点赞成功", null, 1);
        } else {
            redisUtil.like(userId, articleId);
            return new ResponseBean("点赞成功", null, 1);
        }
    }

    /**
     * 已测试
     * 获取文章
     * @param id 文章id
     * @param page 评论的页数
     */
    @GetMapping("/article/{id}")
    public ResponseBean getArticle(@PathVariable Long id, @RequestParam("page") int page) {
        PackedArticle article = articleService.getArticle(id);
        if (article == null) {
            throw new BadRequestException("文章不存在");
        }
        System.out.println(article);
        LinkedHashMap<String, Object> articleAndComment = new LinkedHashMap<>(6);
        //获得文章信息
        if (page == 1) {
            articleAndComment.put("article", article);
        }

        //是否已点过赞
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        boolean liked = redisUtil.isLiked(user.getId(), id);
        articleAndComment.put("isLiked", liked);

        //获得回复信息
        Page<ViewComment> viewCommentPage = new Page<ViewComment>()
                .setCurrent(page)
                .setSize(30);
        List<ViewComment> comments = articleCommentService
                .getCommentsByArticleId(viewCommentPage, id);
        articleAndComment.put("comments", comments);

        return new ResponseBean("获取成功", articleAndComment, 1);
    }

    /**
     * 已测试
     */
    @PostMapping("/swank")
    public ResponseBean postSwank(@RequestBody @Validated Article article
            , BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        saveArticle(article, 0);
        return new ResponseBean("发布成功", null, 1);
    }

    /**
     * 已测试
     */
    @PostMapping("/story")
    public ResponseBean postStory(@RequestBody @Validated Article article
            , BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        saveArticle(article, 1);
        return new ResponseBean("发布成功", null, 1);
    }

    /**
     * 已测试
     */
    @GetMapping("/swank")
    public ResponseBean getSwanks(@RequestParam @Min(value = 1) int page) {

        Page<PackedArticle> pageObj = new Page<PackedArticle>().setCurrent(page);
        List<PackedArticle> swanks = articleService.getArticles(pageObj, 0);
        return new ResponseBean("获取成功", swanks, 1);
    }

    /**
     * 已测试
     */
    @GetMapping("/story")
    public ResponseBean getStories(@RequestParam @Min(value = 1) int page) {

        Page<PackedArticle> pageObj = new Page<PackedArticle>().setCurrent(page);
        List<PackedArticle> stories = articleService.getArticles(pageObj, 1);
        return new ResponseBean("获取成功", stories, 1);
    }

    /**
     * 已测试
     */
    @PostMapping("/comment")
    public ResponseBean postComment(@RequestBody @Validated ArticleComment articleComment, BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        if (articleService.articleExist(articleComment.getToArticle())) {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            Long userId = user.getId();
            articleComment.setAuthorId(userId);
            articleCommentService.save(articleComment);

            sendMessageToArticleAuthor(articleComment);

            return new ResponseBean("发布成功", null, 1);
        }
        throw new BadRequestException("文章不存在");
    }

    /**
     * 已测试
     */
    @PostMapping("/comment/inner")
    public ResponseBean postInnerComment(@RequestBody @Validated InnerComment innerComment, BindingResult bindingResult) {
        System.out.println(innerComment);
        BindingResultUtil.checkBinding(bindingResult);

        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = currentUser.getId();
        innerComment.setUserId(userId);

        Long toCommentId = innerComment.getToComment();
        ArticleComment comment = articleCommentService.getById(toCommentId);
        if (comment == null) {
            throw new BadRequestException("回复的评论不存在");
        }

        sendMessageToArticleAuthor(comment);
        sendMessageToCommentAuthor(innerComment, comment);

        innerCommentService.save(innerComment);
        return new ResponseBean("发送成功", null, 1);
    }

    private void sendMessageToCommentAuthor(InnerComment innerComment, ArticleComment toComment) {
        String toCommentUserId = toComment.getAuthorId().toString();

        if (!WebSocketService.isUserOnline(toCommentUserId)) {
            return;
        }

        //评论作者和评论的回复作者相同不推送
        if (innerComment.getUserId().equals(toComment.getAuthorId())) {
            return;
        }
//        需要修改
//
        WebSocketResponseBean bean = new WebSocketResponseBean("comment", "收到一条新回复", innerComment.getContent());
        try {
            WebSocketService.sendMessage(toCommentUserId, bean);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    private void saveArticle(Article article, int type) {
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        article.setAuthorId(currentUser.getId());
        article.setArticleType(type);
        articleService.save(article);
    }

    private void sendMessageToArticleAuthor(ArticleComment articleComment) {
        String articleAuthorId = articleComment.getAuthorId().toString();

        if (!WebSocketService.isUserOnline(articleAuthorId)) {
            return;
        }

//        给文章作者发送
        String articleCommentAuthorId = articleComment.getAuthorId().toString();
//        如果文章作者和回复人相同则不推送
        if (articleAuthorId.equals(articleCommentAuthorId)) {
            return;
        }
//        需要修改
//
        WebSocketResponseBean bean = new WebSocketResponseBean("comment", "收到一条新回复", articleComment.getContent());
        try {
            WebSocketService.sendMessage(articleAuthorId, bean);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }

//        Long toComment = articleComment.getToComment();
//        if (toComment == null) {
//            return;
//        }
//        给该条评论的作者推送
//        String toCommentAuthorId = articleCommentService
//                .getOne(new QueryWrapper<ArticleComment>()
//                        .eq("id", toComment))
//                .getAuthorId()
//                .toString();
//        如果被回复的评论作者与发表回复的作者相同
//        if (articleCommentAuthorId.equals(toCommentAuthorId)) {
//            return;
//        }
//
//        try {
//            WebSocketService.sendMessage(toCommentAuthorId, bean);
//        } catch (IOException | EncodeException e) {
//            e.printStackTrace();
//        }
    }
}
