package com.giraffe.webchat.controller;

import com.giraffe.webchat.domain.Result;
import com.giraffe.webchat.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 用户登录
 */
@RestController
public class UserController {

    /**
     * 用户登录
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public Result login(User user, HttpSession session) {
        Result result = new Result();
        if (user != null && user.getUsername() != null && "123".equals(user.getPassword())) {
            result.setFlag(true);
            //将用户名存储到session对象中
            session.setAttribute("user", user.getUsername());
        }else {
            result.setFlag(false);
            result.setMessage("登录失败");
        }
        return result;
    }

    /**
     * 取得登录用的的用户名
     * @param session
     * @return
     */
    @RequestMapping("/getUsername")
    public String getUsername(HttpSession session) {
        return (String) session.getAttribute("user");
    }
}