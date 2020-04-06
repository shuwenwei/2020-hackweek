package com.sww.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @TableField
    private Date gmtCreate;
    @Null
    private Date gmtModified;
    @TableField(value = "is_delete")
    @Null
    private Integer delete;
}
