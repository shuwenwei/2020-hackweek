package com.sww.pojo.view;

import lombok.Data;

import java.util.Date;

/**
 * @author sww
 */
@Data
public class ViewInnerComment {
    private Long authorUserId;
    private String authorUsername;
    private String content;
    private String innerAuthorAvatarUrl;
    private Date gmtCreate;
}
