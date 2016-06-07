package com.hwz.websocket.web.controller;

import com.hwz.websocket.consts.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController
{

    /**
     * 登录,注册
     *
     * @param request  请求
     * @param username 用户名
     * @return 跳转主页
     */
    @RequestMapping("login")
    public String login(HttpServletRequest request, @RequestParam("username") String username)
    {
        HttpSession session = request.getSession();
        session.setAttribute(Constants.DEFAULT_SESSION_USERNAME, username);
        session.setAttribute("username",username);
        return "redirect:index";
    }

    //主页
    @RequestMapping("index")
    public String index()
    {
        return "index.html";
    }
}
