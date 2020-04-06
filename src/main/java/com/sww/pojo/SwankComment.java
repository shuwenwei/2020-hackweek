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
@TableName("swank_comment")
public class SwankComment {
    @TableId(type = IdType.ASSIGN_ID)
    @Null
    private Long id;
    @Null
    private Long authorId;
    @NotNull(message = "swankId不能为空")
    private Long toSwank;
    @NotNull(message = "内容不能为空")
    private String content;
    private Long toComment;
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    private Date gmtModified;
    @TableField(value = "is_delete", select = false)
    private Integer delete;
}
