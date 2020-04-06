package com.sww.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sww.exception.BadRequestException;
import com.sww.pojo.*;
import com.sww.service.StoryCommentService;
import com.sww.service.StoryService;
import com.sww.service.SwankService;
import com.sww.service.UserService;
import com.sww.util.BindingResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;


/**
 * @author sww
 */
@RestController
@RequiresRoles("user")
@RequestMapping("/article")
public class ArticleController {

    private SwankService swankService;
    private StoryService storyService;
    private UserService userService;
    private StoryCommentService storyCommentService;

    @Autowired
    public void setStoryCommentService(StoryCommentService storyCommentService) {
        this.storyCommentService = storyCommentService;
    }

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

    @PostMapping("/swank")
    public ResponseBean postSwank(@RequestBody @Validated Swank swank
            , BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        swank.setAuthorId(currentUser.getId());
        swankService.save(swank);
        return new ResponseBean("发布成功", null, 1);
    }

    @PostMapping("/story")
    public ResponseBean postStory(@RequestBody @Validated Story story
            , BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        story.setId(currentUser.getId());
        storyService.save(story);
        return new ResponseBean("发布成功", null, 1);
    }

    /**
    @GetMapping("/story")
    public ResponseBean getStory(@RequestParam @Min(value = 1) int page, BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        QueryWrapper<Story> wrapper = new QueryWrapper<>();
        List<Story> records = storyService
                .page(new Page<Story>().setCurrent(page))
                .getRecords();
        return new ResponseBean("获取成功", records, 1);
    }
    */
//

    @PostMapping("/story/comment")
    public ResponseBean postStoryComment(@RequestBody @Validated StoryComment storyComment, BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        if (storyService.storyExist(storyComment.getToStory())) {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            storyComment.setAuthorId(user.getId());
            storyCommentService.save(storyComment);
            return new ResponseBean("发布成功", null, 1);
        }
        throw new BadRequestException("story不存在");
    }


}
