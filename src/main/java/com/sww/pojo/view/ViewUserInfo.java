package com.sww.pojo.view;

import lombok.Data;

import java.util.Date;

/**
 * @author sww
 */
@Data
public class ViewUserInfo {
    private String username;
    private Long userId;
    private String avatar;
    private Integer followNum;
    private Integer followedNum;
    private Integer likedNum;
    private String introduction;
    private Date birth;
}
