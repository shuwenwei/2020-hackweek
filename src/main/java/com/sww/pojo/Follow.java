package com.sww.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author sww
 */
@Data
@TableName(value = "follow")
public class Follow {
    @TableId(type = IdType.ASSIGN_ID)
    @Null
    private Long id;
    @Null
    private Long userId;
    @NotNull
    private Long followedUserId;
}
