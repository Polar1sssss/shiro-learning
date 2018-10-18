package com.hujtb.shiro.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;

import java.util.Date;

/**
 * @author hujtb
 * @create on 2018-10-17-18:34
 */
public class ShiroService {

    /**
     * 只有admin权限才能访问
     */
    @RequiresRoles(value={"admin"})
    public void testMethod(){
        System.out.println("ShiroService:" + new Date());
        Session session = SecurityUtils.getSubject().getSession();
        System.out.println(session.getAttribute("key"));
    }
}
