package com.sww.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sww
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketResponseBean {
    private String type;
    private String msg;
    private Object data;

    public static WebSocketResponseBean REQUIRE_LOGIN =
            new WebSocketResponseBean("exception", "require login", null);
    public static WebSocketResponseBean ERROR =
            new WebSocketResponseBean("exception", "error", null);

}
