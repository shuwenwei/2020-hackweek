package com.sww.pojo.view;

import lombok.Data;

import java.util.Date;

/**
 * @author sww
 */
@Data
public class PackedArticle {
    private Long id;
    private String title;
    private String authorUsername;
    private Long authorId;
    private String authorAvatarUrl;
    private Date gmtCreate;
}
