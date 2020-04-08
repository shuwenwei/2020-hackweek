package com.sww.pojo.view;

import lombok.Data;

import java.util.Date;


/**
 * @author sww
 */
@Data
public class ViewComment {
    private Long authorId;
    private String authorUsername;
    private String content;
    private String avatarUrl;
    private Date gmtCreate;
}
