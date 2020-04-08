package com.sww.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.validation.constraints.Null;
import java.util.Date;

/**
 * @author sww
 */
@TableName("user_info")
@Data
public class UserInfo {
    @TableId(type = IdType.ASSIGN_ID)
    @Null
    private Long id;
    @Null
    private Long userId;
    @Null
    private String avatarUrl;
    @Null
    private Integer followNum;
    @Null
    private Integer followedNum;
    @Null
    private Integer likedNum;
    private Date birth;
    private String introduction;
}
