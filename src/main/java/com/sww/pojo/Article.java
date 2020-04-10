package com.sww.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * @author sww
 */
@Data
@TableName("article")
public class Article {
    @TableId(type = IdType.ASSIGN_ID)
    @Null
    private Long id;
    @NotNull(message = "标题不能为空")
    private String title;
    /**
     * 类型
     * 0:swank
     * 1:story
     */
    @Null
    private Integer articleType;
    @Null
    private Long authorId;
    @NotNull(message = "内容不能为空")
    private String content;
    @Null
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @Null
    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    private Date gmtModified;
    @TableField(value = "is_delete", select = false, fill = FieldFill.INSERT)
    @Null
    @TableLogic
    private Integer delete;
}
