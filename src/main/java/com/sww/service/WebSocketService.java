package com.sww.service;

import com.sww.config.WebSocketConfig;
import org.springframework.stereotype.Service;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
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

    public static boolean sendMessage(String target, String message) throws IOException {
        WebSocketService service = services.get(target);
        if (service == null) {
            return false;
        }
        services.get(target)
                .getSession()
                .getBasicRemote()
                .sendText(message);
        return true;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        String username = (String) session.getUserProperties().get("username");
        services.put(username, this);
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
