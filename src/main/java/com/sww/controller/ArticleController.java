package com.sww.controller;

import com.sww.exception.BadRequestException;
import com.sww.pojo.*;
import com.sww.pojo.packed.PackedArticle;
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
    public ResponseBean getSwank(@RequestParam @Min(value = 1) int page, BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        List<PackedArticle> swanks = articleService.getArticles(page, 0);
        return new ResponseBean("获取成功", swanks, 1);
    }

    @GetMapping("/story")
    public ResponseBean getStory(@RequestParam @Min(value = 1) int page, BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        List<PackedArticle> stories = articleService.getArticles(page, 1);
        return new ResponseBean("获取成功", stories, 1);
    }

    @PostMapping("/comment")
    public ResponseBean postComment(@RequestBody @Validated ArticleComment articleComment, BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        if (articleService.articleExist(articleComment.getToStory())) {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            Long userId = user.getId();
            articleComment.setAuthorId(userId);
            articleCommentService.save(articleComment);

            sendMessageToWebsocket(userId);

            return new ResponseBean("发布成功", null, 1);
        }
        throw new BadRequestException("文章不存在");
    }

    private void saveArticle(Article article, int type) {
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        article.setId(currentUser.getId());
        article.setArticleType(type);
        articleService.save(article);
    }

    private void sendMessageToWebsocket(Long id) {
        String userId = id.toString();
        if (WebSocketService.isUserOnline(userId)) {
            WebSocketResponseBean bean = new WebSocketResponseBean("comment", "收到一条新回复", null);
            try {
                WebSocketService.sendMessage(userId, bean);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        }
    }
}
