package com.sww.pojo.view;

import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * @author sww
 */
@Data
public class ViewComment {
    private Long commentId;
    private Long authorId;
    private String authorUsername;
    private String content;
    private String avatarUrl;
    private Date gmtCreate;

    private List<ViewInnerComment> innerComments;
}
