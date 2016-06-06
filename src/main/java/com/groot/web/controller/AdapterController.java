package com.groot.web.controller;

import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by XiaoWei on 2015/1/7.
 */
@Controller
@RequestMapping("auth")
public class AdapterController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getTs(@RequestParam(value = "error", required = false, defaultValue = "false") boolean error,
                        @RequestParam(value = "username", required = true) String userName,
                        ModelMap map) {
        map.put("userName", userName);
        return "login";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
