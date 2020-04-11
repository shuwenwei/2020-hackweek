package com.sww.controller;

import com.sww.pojo.ResponseBean;
import com.sww.pojo.view.PackedArticle;
import com.sww.pojo.view.ViewListUser;
import com.sww.service.ArticleService;
import com.sww.service.UserInfoService;
import com.sww.util.RedisUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author sww
 */
@RestController
@RequiresRoles("user")
public class RecommendController {
    private RedisUtil redisUtil;
    private ArticleService articleService;
    private UserInfoService userInfoService;

    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 已测试
     * 按点赞数排行进行推荐文章
     */
    @GetMapping("/most/like")
    public ResponseBean getMostLikedArticle() {
        Set<Object> mostLiked = redisUtil.getMostLiked();
        List<PackedArticle> mostLikedArticles = articleService.getArticlesBySet(mostLiked);
        return new ResponseBean("获取成功", mostLikedArticles, 1);
    }

    @GetMapping("/most/followed")
    public ResponseBean getMostFollowersUsers() {
        Set<Object> mostFollowed = redisUtil.getMostFollowed();
        List<ViewListUser> mostFollowersUsers = userInfoService.getViewListUsers(mostFollowed);
        return new ResponseBean("获取成功", mostFollowersUsers, 1);
    }

}
