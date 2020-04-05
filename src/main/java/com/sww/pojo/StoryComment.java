package com.sww.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author sww
 */
@Data
@TableName("story_comment")
public class StoryComment {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long authorId;
    private String content;
    private Long toComment;
    private Date gmtCreate;
    private Date gmtUpdate;
    @TableField(value = "is_delete")
    private Integer delete;
}
