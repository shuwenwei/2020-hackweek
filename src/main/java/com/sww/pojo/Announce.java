package com.sww.pojo;


import lombok.Data;

/**
 * @author sww
 */
@Data
public class Announce {
    private Long id;
    private Long userId;
    private String type;
    private Long contentId;
    private Long fromUser;
}
