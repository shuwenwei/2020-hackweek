package com.sww.pojo;

import com.baomidou.mybatisplus.annotation.*;
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
    @TableField(fill = FieldFill.INSERT, select = false)
    private Date gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    private Date gmtModified;
    @TableField(value = "is_delete", select = false)
    private Integer delete;
}
