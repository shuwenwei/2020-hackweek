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
@TableName("swank")
public class Swank {
    @TableId(type = IdType.ASSIGN_ID)
    @NotNull(message = "id不能为空")
    private Long id;
    @NotNull(message = "标题不能为空")
    private String title;
    @Null
    private Long authorId;
    @NotNull(message = "内容不能为空")
    private String content;
    @Null
    private Integer likeNum;
    @Null
    private Integer starNum;
    @Null
    @TableField(fill = FieldFill.INSERT, select = false)
    private Date gmtCreate;
    @Null
    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    private Date gmtModified;
    @TableField(value = "is_delete", select = false)
    @Null
    private Integer delete;
}
