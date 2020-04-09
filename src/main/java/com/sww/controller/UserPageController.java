package com.sww.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sww.exception.BadRequestException;
import com.sww.pojo.Article;
import com.sww.pojo.ResponseBean;
import com.sww.pojo.User;
import com.sww.pojo.UserInfo;
import com.sww.pojo.view.ViewListUser;
import com.sww.service.ArticleService;
import com.sww.service.UserInfoService;
import com.sww.util.QiniuUtil;
import com.sww.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author sww
 */
@RestController
public class UserPageController {

    private UserInfoService userInfoService;
    private ArticleService articleService;
    private QiniuUtil qiniuUtil;
    private RedisUtil redisUtil;
    private static final String URL_PREFIX = "http://q89jpbw7d.bkt.clouddn.com/";

    @SuppressWarnings("SpellCheckingInspection")
    @Autowired
    public void setQiniuUtil(QiniuUtil qiniuUtil) {
        this.qiniuUtil = qiniuUtil;
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * 获取用户粉丝
     */
    @GetMapping("/self/follower/{userId}")
    public ResponseBean getUserFollowers(@PathVariable Long userId) {
        if (userInfoService.getUserInfo(userId) == null) {
            throw new BadRequestException("用户不存在");
        }
        Set followers = redisUtil.getFollowers(userId);
        if (followers.isEmpty()) {
            return new ResponseBean("没有被任何人关注", null, 1);
        }
        List<ViewListUser> viewFollowers = userInfoService.getViewListUsers(followers);

        return new ResponseBean("获取成功", viewFollowers, 1);
    }

    @GetMapping("/self/follows/{userId}")
    public ResponseBean getUserFollows(@PathVariable Long userId) {
        if (userInfoService.getUserInfo(userId) == null) {
            throw new BadRequestException("用户不存在");
        }
        Set follows = redisUtil.getFollows(userId);

        if (follows.isEmpty()) {
            return new ResponseBean("没有关注任何人", null, 1);
        }
        List<ViewListUser> viewFollows = userInfoService.getViewListUsers(follows);
        return new ResponseBean("获取成功", viewFollows, 1);

    }

    /**
     * 已测试
     * 获取个人资料
     */
    @GetMapping("/self/main")
    public ResponseBean getSelfPage() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        UserInfo userInfo = userInfoService.getUserInfo(user.getId());

        Long userId = user.getId();
        Integer followed = redisUtil.getFollowedNum(userId);
        Integer liked = redisUtil.getLikedNum(userId);
        Integer follow = redisUtil.getFollowNum(userId);
        userInfo.setFollowNum(follow);
        userInfo.setLikedNum(liked);
        userInfo.setFollowedNum(followed);

        return new ResponseBean("获取成功", userInfo, 1);
    }

    /**
     * 已测试
     * 修改用户的个人信息 需要introduction和birth
     */
    @PutMapping("/self/modify")
    public ResponseBean updateSelfInfo(@RequestBody @Validated UserInfo userInfo) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Date birth = userInfo.getBirth();
        String introduction = userInfo.getIntroduction();
        if (birth == null && introduction == null) {
            throw new BadRequestException("缺少需要更新的信息");
        }
        Long userId = user.getId();
        userInfo.setUserId(userId);

//        boolean update = userInfoService
//                .update(userInfo, new UpdateWrapper<UserInfo>()
//                        .eq("user_id", userId));
        boolean update = userInfoService
                .updateUserInfo(userInfo, new UpdateWrapper<UserInfo>().
                        eq("user_id", userId));
        if (!update) {
            throw new BadRequestException("更新失败");
        }

        return new ResponseBean("更新成功", null, 1);
    }

    /**
     * 查看某人的个人信息
     */
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

//        是否已关注
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        boolean isFollowed = redisUtil.isFollowed(currentUser.getId(), userId);
        pageInfo.put("isFollowed", isFollowed);

        if (page == 1) {
            pageInfo.put("userInfo", userInfo);
        }
        pageInfo.put("articles", articles);

        return new ResponseBean("获取成功", pageInfo, 1);
    }

    /**
     * 已测试
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
        url = URL_PREFIX + url;

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        System.out.println(user == null);
        UserInfo userInfo = new UserInfo();
        Long userId = Objects.requireNonNull(user).getId();
        userInfo.setUserId(userId);
        userInfo.setAvatarUrl(url);
        userInfoService
                .update(userInfo, new UpdateWrapper<UserInfo>()
                        .eq("user_id", userId));

        return new ResponseBean("上传成功", url, 1);
    }
}
