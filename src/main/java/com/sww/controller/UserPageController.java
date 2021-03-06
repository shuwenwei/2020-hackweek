package com.sww.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sww.exception.BadRequestException;
import com.sww.pojo.*;
import com.sww.pojo.view.ViewAnnounce;
import com.sww.pojo.view.ViewListUser;
import com.sww.pojo.view.ViewUserInfo;
import com.sww.service.AnnounceService;
import com.sww.service.ArticleService;
import com.sww.service.UserInfoService;
import com.sww.util.QiniuUtil;
import com.sww.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
@RequiresRoles("user")
public class UserPageController {

    private UserInfoService userInfoService;
    private ArticleService articleService;
    private AnnounceService announceService;
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
     *
     */
    @GetMapping("/self/announce")
    public ResponseBean getAnnounce(@RequestParam @Min(1) int page) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Page<ViewAnnounce> announcePage = new Page<ViewAnnounce>().setCurrent(page);
        List<ViewAnnounce> announceList = announceService
                .getViewAnnounceList(announcePage, user.getId())
                .getRecords();
        if (announceList.isEmpty()) {
            return new ResponseBean("没有消息", null, 0);
        }
        return new ResponseBean("获取成功", announceList, 1);
    }

    /**
     * 获取用户粉丝
     */
    @GetMapping("/self/follower/{userId}")
    public ResponseBean getUserFollowers(@PathVariable Long userId) {
        if (userInfoService.getUserInfo(userId) == null) {
            throw new BadRequestException("用户不存在");
        }
        Set<Object> followers = redisUtil.getFollowers(userId);
        if (followers.isEmpty()) {
            return new ResponseBean("没有被任何人关注", null, 0);
        }
        List<ViewListUser> viewFollowers = userInfoService.getViewListUsers(followers);

        return new ResponseBean("获取成功", viewFollowers, 1);
    }

    /**
     * 获取用户关注的人
     */
    @GetMapping("/self/follows/{userId}")
    public ResponseBean getUserFollows(@PathVariable Long userId) {
        if (userInfoService.getUserInfo(userId) == null) {
            throw new BadRequestException("用户不存在");
        }
        Set<Object> follows = redisUtil.getFollows(userId);

        if (follows.isEmpty()) {
            return new ResponseBean("没有关注任何人", null, 0);
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
        ViewUserInfo userInfo = userInfoService.getUserInfo(user.getId());

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
    @PutMapping("/self/main")
    public ResponseBean updateSelfInfo(@RequestBody @Validated UserInfo userInfo) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Date birth = userInfo.getBirth();
        String introduction = userInfo.getIntroduction();
        if (birth == null && introduction == null) {
            throw new BadRequestException("缺少需要更新的信息");
        }
        Long userId = user.getId();
        userInfo.setUserId(userId);

        boolean update = userInfoService
                .updateUserInfo(userInfo, new UpdateWrapper<UserInfo>().
                        eq("user_id", userId));
        if (!update) {
            throw new BadRequestException("更新失败");
        }

        return new ResponseBean("更新成功", null, 1);
    }

    /**
     * 已测试
     * 查看某人的个人信息
     */
    @GetMapping("/self/{userId}")
    public ResponseBean getUserInfo(@PathVariable Long userId, @RequestParam @Min(1) int page) {
        ViewUserInfo userInfo = userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            throw new BadRequestException("用户信息为空或用户不存在");
        }
        Page<Article> articlePage = new Page<>();
        articlePage.setCurrent(page);
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>()
                .eq("author_id", userId)
                .orderByDesc("gmt_create");
        List<Article> articles = articleService
                .page(articlePage, wrapper)
                .getRecords();
        Map<String, Object> pageInfo = new HashMap<>(6);

        if (page == 1) {
            User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
            //        是否已关注
            boolean isFollowed = redisUtil.isFollowed(currentUser.getId(), userId);
            pageInfo.put("isFollowed", isFollowed);
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

    @PostMapping("/follow/{followedUserId}")
    public ResponseBean followUser(@PathVariable Long followedUserId) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        当前用户id
        Long userId = user.getId();

        if (userInfoService.getUserInfo(followedUserId) == null) {
            throw new BadRequestException("关注的用户不存在");
        } else if (userId.equals(followedUserId)) {
            throw new BadRequestException("无法关注");
        }

        boolean isFollow = redisUtil.sHasKey("follow::" + userId, followedUserId);
        if (isFollow) {
            redisUtil.unfollowUser(userId, followedUserId);
            return new ResponseBean("取消关注成功", null, 1);
        }
        redisUtil.followUser(userId, followedUserId);
        return new ResponseBean("关注成功", null, 1);
    }
}
