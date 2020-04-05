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
@TableName("story")
public class Story {
    @TableId(type = IdType.ASSIGN_ID)
    @NotNull
    private Long id;
    @NotNull
    private String title;
    @Null
    private Long authorId;
    @NotNull
    private String content;
    private Integer likeNum;
    private Integer starNum;
    private Date gmtCreate;
    private Date gmtUpdate;
    @TableField(value = "is_delete")
    private Integer delete;
}
