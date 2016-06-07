package com.hwz.websocket.websocket;

import com.alibaba.fastjson.JSON;
import com.hwz.websocket.consts.Constants;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

public class TextMessageHandler extends TextWebSocketHandler
{

    private static final Map<String, WebSocketSession> users;

    static
    {
        users = new HashMap<String, WebSocketSession>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception
    {
        /*
         * 链接成功后会触发此方法，可在此处对离线消息什么的进行处理
         */
        users.put(session.getId(), session);String username = (String) session.getAttributes().get(Constants.DEFAULT_WEBSOCKET_USERNAME);
        System.out.println(username + " connect success ...");
        Map<String ,Object> map = new HashMap<String, Object>();
        map.put("type","users");

        List<String> userList = new ArrayList<String>();
        Iterator<Map.Entry<String, WebSocketSession>> it = userIterator();
        while (it.hasNext())
        {
            WebSocketSession webSocketSession = it.next().getValue();
            userList.add((String) webSocketSession.getAttributes().get(Constants.DEFAULT_WEBSOCKET_USERNAME));
        }

        map.put("users",userList);
        String str = JSON.toJSONString(map);
        System.out.println(str);
        sendMessageToUsers(new TextMessage(str));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception
    {
        super.handleMessage(session, message);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception
    {
        /*
         * 前端 websocket.send() 会触发此方法 
         */
        System.out.println("message -> " + message.getPayload());
        super.handleTextMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception
    {
        if (session.isOpen())
        {
            session.close();
        }
        System.err.println(exception.getMessage());
        System.out.println("websocket connection closed......");
        users.remove(session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception
    {
        System.out.println("websocket connection closed......");
        users.remove(session.getId());
    }

    public void sendMessageToUser(String username, TextMessage message)
    {
        Iterator<Map.Entry<String, WebSocketSession>> it = userIterator();
        while (it.hasNext())
        {
            WebSocketSession session = it.next().getValue();

            if (username.equals(session.getAttributes().get(Constants.DEFAULT_WEBSOCKET_USERNAME)))
            {
                try
                {
                    if (session.isOpen())
                    {
                        session.sendMessage(message);
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMessageToUsers(TextMessage message)
    {
        Iterator<Map.Entry<String, WebSocketSession>> it = userIterator();
        while (it.hasNext())
        {
            WebSocketSession session = it.next().getValue();
            try
            {
                if (session.isOpen())
                    session.sendMessage(message);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    private Iterator<Map.Entry<String, WebSocketSession>> userIterator()
    {
        Set<Map.Entry<String, WebSocketSession>> entrys = users.entrySet();
        return entrys.iterator();
    }
}
