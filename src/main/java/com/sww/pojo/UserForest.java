package com.sww.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author sww
 */
@Data
@TableName("forest")
public class UserForest {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long userId;
    private Date date;
    private Integer onlineTime;
}
