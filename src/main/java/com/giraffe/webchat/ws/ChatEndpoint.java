package com.giraffe.webchat.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giraffe.webchat.domain.Message;
import com.giraffe.webchat.domain.ResultMessage;
import com.giraffe.webchat.utils.MessageUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat",configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndpoint {

    //在线客户端的endpoint对象，通过这个对象可以管理在线客户，可以和在线客户端交互
    private static Map<String,ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();
    //websocket session 对象用于沟通客户端
    private Session session;
    //http session 对象用于保存客户端信息
    private HttpSession httpSession;

    @OnOpen
    //连接建立时被调用
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        //获取httpSession对象
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        String username = (String) this.httpSession.getAttribute("user");
        //加入在线客户对象
        onlineUsers.put(username,this);
        //广播
        String message = MessageUtils.getMessage(true,null,onlineUsers.keySet());
        broadcastAllUsers(message);
    }

    private void broadcastAllUsers(String message) {
        for (ChatEndpoint user : onlineUsers.values()) {
            try {
                user.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnMessage
    //接收到客户端发送的数据时被调用
    public void onMessage(String message, Session session) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            //接收信息并转成Message对象
            Message mess= mapper.readValue(message, Message.class);

            //发送信息给对应客户的客户端
            String fromName = (String) httpSession.getAttribute("user");
            String resultMessage = MessageUtils.getMessage(false,fromName,mess.getMessage());
            ChatEndpoint chatEndpoint = onlineUsers.get(mess.getToName());
            chatEndpoint.session.getBasicRemote().sendText(resultMessage);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnClose
    //关闭连接时被调用
    public void onClose(Session session, CloseReason closeReason) {}
}
