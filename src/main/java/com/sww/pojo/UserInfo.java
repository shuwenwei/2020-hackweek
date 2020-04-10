package com.sww.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * @author sww
 */
@TableName("user_info")
@Data
public class UserInfo implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    @Null
    @JsonIgnore
    private Long id;
    @Null
    private Long userId;
    @Null
    private String avatarUrl;
    @Null
    @TableField(select = false)
    private Integer followNum;
    @Null
    @TableField(select = false)
    private Integer followedNum;
    @Null
    @TableField(select = false)
    private Integer likedNum;
    private Date birth;
    @Length(max = 100)
    private String introduction;
}
