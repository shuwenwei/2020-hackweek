package com.sww.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author sww
 */
@TableName("user_info")
@Data
public class UserInfo {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long userId;
    private String avatarUrl;
    private Integer followNum;
    private Integer likedNum;
    private Date birth;
    private String introduction;
}
