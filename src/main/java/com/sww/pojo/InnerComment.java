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
public class InnerComment {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @Null
    private Long authorId;
    @NotNull(message = "内容不能为空")
    private String content;
    @NotNull(message = "toComment不能为空")
    private Long toComment;
    @TableField(fill = FieldFill.INSERT)
    @Null
    private Date gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    @Null
    private Date gmtModified;
    @TableLogic
    @Null
    @TableField(fill = FieldFill.INSERT, select = false, value = "is_delete")
    private boolean delete;
}
