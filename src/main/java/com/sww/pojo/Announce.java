package com.sww.pojo;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author sww
 */
@Data
@TableName(value = "announce")
@NoArgsConstructor
public class Announce {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long userId;
    private String type;
    private Long inArticleId;
    private String content;
    private Long fromUser;
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    public Announce(Long userId, String type, Long inArticleId, String content, Long fromUser) {
        this.userId = userId;
        this.type = type;
        this.inArticleId = inArticleId;
        this.content = content;
        this.fromUser = fromUser;
    }
}
