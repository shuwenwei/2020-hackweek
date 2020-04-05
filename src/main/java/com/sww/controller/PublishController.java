package com.sww.controller;

import com.sww.exception.BadRequestException;
import com.sww.pojo.ResponseBean;
import com.sww.pojo.Story;
import com.sww.pojo.Swank;
import com.sww.pojo.User;
import com.sww.service.StoryService;
import com.sww.service.SwankService;
import com.sww.service.UserService;
import com.sww.util.BindingResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author sww
 */
@RestController
@RequiresRoles("user")
public class PublishController {

    private SwankService swankService;
    private StoryService storyService;
    private UserService userService;


    @Autowired
    public void setStoryService(StoryService storyService) {
        this.storyService = storyService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSwankService(SwankService swankService) {
        this.swankService = swankService;
    }

    @PostMapping("/article/swank")
    public ResponseBean postSwank(@RequestBody @Validated Swank swank
            , BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        if (currentUser.getId().equals(swank.getAuthorId())) {
            swank.setAuthorId(currentUser.getId());
            swankService.save(swank);
            return new ResponseBean("发布成功", null, 1);
        }
        throw new BadRequestException("发布失败");
    }

    @PostMapping("/article/story")
    public ResponseBean postStory(@RequestBody @Validated Story story
            , BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        if (currentUser.getId().equals(story.getAuthorId())) {
            story.setAuthorId(currentUser.getId());
            storyService.save(story);
            return new ResponseBean("发布成功", null, 1);
        }
        throw new BadRequestException("发布失败");
    }

}
