package com.hujtb.shiro.handlers;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hujtb
 * @create on 2018-10-11-14:13
 */

//需要将spring-context的jar包设置成compile
@Controller
@RequestMapping("/shiro")
public class ShiroHandler {

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("pwd") String password){
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            try {
                //token传递到ShiroRealm里面
                currentUser.login(token);
            } catch (AuthenticationException ae) {
            }
        }
        return "redirect:/list.jsp";
    }
}
