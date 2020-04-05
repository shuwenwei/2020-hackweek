package com.sww.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sww
 */
@Data
@AllArgsConstructor
public class ResponseBean {
    private String message;
    private Object data;
    private int status;
}
