package com.sww.service;

import com.alibaba.fastjson.JSON;
import com.sww.config.WebSocketConfig;
import com.sww.pojo.WebSocketResponseBean;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sww
 */
@Service
@ServerEndpoint(value = "/ws", configurator = WebSocketConfig.class)
public class WebSocketService {

    private static ConcurrentHashMap<String, WebSocketService> services =
            new ConcurrentHashMap<>();
    private Session session;

    public Session getSession() {
        return session;
    }

    public static boolean sendMessage(String target, WebSocketResponseBean message) throws IOException, EncodeException {
        WebSocketService service = services.get(target);
        if (service == null) {
            return false;
        }
        services.get(target)
                .getSession()
                .getBasicRemote()
                .sendObject(message);
        return true;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        String username = (String) session.getUserProperties().get("username");
        if (username != null) {
            services.put(username, this);
            return;
        }
        try {
            session.getBasicRemote().sendText(JSON.toJSONString(WebSocketResponseBean.REQUIRE_LOGIN));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        String username = (String) session.getUserProperties().get("username");
        services.remove(username, this);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
