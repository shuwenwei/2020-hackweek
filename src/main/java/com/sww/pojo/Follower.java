package com.sww.pojo;

import lombok.Data;

import java.util.Set;

/**
 * @author sww
 */
@Data
public class Follower {
    private Long id;
    private Long userId;
    private Set<User> followers;
}
