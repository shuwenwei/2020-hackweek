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
    private UserService userService;
    private ArticleCommentService articleCommentService;

    @Autowired
    public void setArticleCommentService(ArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/swank")
//    public ResponseBean postSwank(@RequestBody @Validated Swank swank
//            , BindingResult bindingResult) {
//        BindingResultUtil.checkBinding(bindingResult);
//
//        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
//        swank.setAuthorId(currentUser.getId());
//        swankService.save(swank);
//        return new ResponseBean("发布成功", null, 1);
//    }

    @PostMapping("/")
    public ResponseBean postArticle(@RequestBody @Validated Article article
            , BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        article.setId(currentUser.getId());
        articleService.save(article);
        return new ResponseBean("发布成功", null, 1);
    }

    @GetMapping("/")
    public ResponseBean getArticle(@RequestParam @Min(value = 1) int page, BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);
//
//
        List<PackedArticle> stories = articleService.getArticles(page);
        return new ResponseBean("获取成功", stories, 1);
    }

    @PostMapping("/comment")
    public ResponseBean postArticleComment(@RequestBody @Validated ArticleComment articleComment, BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        if (articleService.articleExist(articleComment.getToStory())) {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            Long userId = user.getId();
            articleComment.setAuthorId(userId);
            articleCommentService.save(articleComment);

            sendMessageToWebsocket(userId);

            return new ResponseBean("发布成功", null, 1);
        }
        throw new BadRequestException("story不存在");
    }



//    @PostMapping("/swank/comment")
//    public ResponseBean postSwankComment(@RequestBody @Validated SwankComment swankComment, BindingResult bindingResult) {
//        BindingResultUtil.checkBinding(bindingResult);
//
//        if (swankService.swankExist(swankComment.getToSwank())) {
//            User user = (User) SecurityUtils.getSubject().getPrincipal();
//            Long userId = user.getId();
//            swankComment.setAuthorId(userId);
//            swankCommentService.save(swankComment);
//
//            sendMessageToWebsocket(userId);
//
//            return new ResponseBean("发布成功", null, 1);
//        }
//        throw new BadRequestException("swank不存在");
//    }

    private void sendMessageToWebsocket(Long id) {
        String userId = id.toString();
        if (WebSocketService.isUserOnline(userId)) {
            WebSocketResponseBean bean = new WebSocketResponseBean("comment", "收到一条新回复", null);
            try {
                WebSocketService.sendMessage(userId, bean);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        }
    }
}
