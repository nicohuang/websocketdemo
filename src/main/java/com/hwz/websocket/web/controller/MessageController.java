package com.hwz.websocket.web.controller;

import com.alibaba.fastjson.JSON;
import com.hwz.websocket.websocket.TextMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("message")
public class MessageController
{

    @Bean
    public TextMessageHandler textMessageHandler()
    {
        return new TextMessageHandler();
    }

    @RequestMapping
    public String view()
    {
        return "message.html";
    }

    @RequestMapping(value = "send", method = RequestMethod.POST)
    @ResponseBody
    public void send(@RequestBody SendForm form, HttpSession session)
    {
        //发送

        String myuser = (String) session.getAttribute("username");
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("username", form.getUsername());
//        map.put("sender", myuser);
//        map.put("message", form.getMessage());

        Message newMessage = new Message(form.getUsername(),form.getMessage(),myuser,"message");
        String messageStr = JSON.toJSONString(newMessage);
        TextMessage message = new TextMessage(messageStr);
        textMessageHandler().sendMessageToUser(form.getUsername(), message);

        //推给自己
        newMessage.setUsername(myuser);
        String messageStr1 = JSON.toJSONString(newMessage);
        TextMessage message1 = new TextMessage(messageStr1);
        textMessageHandler().sendMessageToUser(myuser, message1);
    }
}
