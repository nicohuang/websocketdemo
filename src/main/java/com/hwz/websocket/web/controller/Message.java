package com.hwz.websocket.web.controller;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by nico on 16/6/7.
 */
public class Message implements Serializable
{
    @JSONField(name = "username")
    private String username;

    @JSONField(name = "message")
    private String message;

    @JSONField(name = "sender")
    private String sender;

    @JSONField(name = "type")
    private String type;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getSender()
    {
        return sender;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Message(String username, String message, String sender, String type)
    {
        this.username = username;
        this.message = message;
        this.sender = sender;
        this.type = type;
    }

    public Message()
    {
    }
}
