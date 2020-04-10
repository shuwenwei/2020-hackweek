package com.sww.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * @author sww
 */
@Data
@TableName("article_comment")
public class ArticleComment {
    @TableId(type = IdType.ASSIGN_ID)
    @Null
    private Long id;
    @Null
    private Long authorId;
    @NotNull(message = "articleId不能为空")
    private Long toArticle;
    @NotEmpty(message = "内容不能为空")
    private String content;
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    private Date gmtModified;
    @TableField(value = "is_delete", select = false, fill = FieldFill.INSERT)
    @TableLogic
    private Integer delete;
}
