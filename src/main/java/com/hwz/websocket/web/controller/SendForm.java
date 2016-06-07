package com.hwz.websocket.web.controller;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by nico on 16/6/7.
 */
public class SendForm implements Serializable
{
    @JSONField(name = "username")
    private String username;

    @JSONField(name = "message")
    private String message;

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
}
