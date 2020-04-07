package com.sww.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sww.exception.BadRequestException;
import com.sww.pojo.Article;
import com.sww.pojo.ResponseBean;
import com.sww.pojo.User;
import com.sww.pojo.UserInfo;
import com.sww.service.ArticleService;
import com.sww.service.UserInfoService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sww
 */
@RestController
public class UserPageController {

    private UserInfoService userInfoService;
    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/self/modify")
    public ResponseBean getSelfPage() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        UserInfo userInfo = userInfoService.getUserInfo(user.getId());
        return new ResponseBean("获取成功", userInfo, 1);
    }

    @GetMapping("/self/{userId}")
    public ResponseBean getUserInfo(@PathVariable Long userId, @RequestParam @Min(1) int page) {
        UserInfo userInfo = userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            throw new BadRequestException("用户信息为空或用户不存在");
        }
        Page<Article> articlePage = new Page<>();
        articlePage.setCurrent(page);
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>()
                .eq("author_id", userId);
        List<Article> articles = articleService
                .page(articlePage, wrapper)
                .getRecords();
        Map<String, Object> pageInfo = new HashMap<>(4);

        if (page == 1) {
            pageInfo.put("userInfo", userInfo);
        }
        pageInfo.put("articles", articles);

        return new ResponseBean("获取成功", pageInfo, 1);
    }

}
