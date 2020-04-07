package com.sww.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sww.exception.BadRequestException;
import com.sww.pojo.Article;
import com.sww.pojo.ResponseBean;
import com.sww.pojo.User;
import com.sww.pojo.UserInfo;
import com.sww.service.ArticleService;
import com.sww.service.UserInfoService;
import com.sww.util.QiniuUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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
    private QiniuUtil qiniuUtil;
    private static final String urlPrefix = "http://q89jpbw7d.bkt.clouddn.com/";

    @SuppressWarnings("SpellCheckingInspection")
    @Autowired
    public void setQiniuUtil(QiniuUtil qiniuUtil) {
        this.qiniuUtil = qiniuUtil;
    }

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

    /**
     * 上传用户头像
     * @param avatar 用户头像
     */
    @PostMapping("/self/avatar")
    public ResponseBean updateAvatar(@RequestParam("avatar")MultipartFile avatar) throws IOException {
        if (avatar == null) {
            throw new BadRequestException("上传失败");
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(avatar.getBytes());
        String url = qiniuUtil.uploadPicture(inputStream);
        if (url.length() < 1) {
            throw new BadRequestException("上传失败");
        }
        url = urlPrefix + url;

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        System.out.println(user == null);
        UserInfo userInfo = new UserInfo();
        Long userId = user.getId();
        userInfo.setUserId(userId);
        userInfo.setAvatarUrl(url);
        userInfoService
                .update(userInfo, new UpdateWrapper<UserInfo>()
                        .eq("user_id", userId));

        return new ResponseBean("上传成功", url, 1);
    }
}
