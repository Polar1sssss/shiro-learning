package com.hujtb.shiro.factory;

import java.util.LinkedHashMap;

/**
 * 配置工厂类，实际上是LinkedMap，里面存放着各个页面的访问权限
 * @author hujtb
 * @create on 2018-10-17-18:45
 */
public class FilterChainDefinitionMapBuilder {

    public LinkedHashMap<String, String> buildFilterChainDefination(){
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        //要按照顺序写
        map.put("/login.jsp", "anon");
        map.put("/shiro/logout.jsp", "logout");
        map.put("/user.jsp", "roles[user]");
        map.put("/admin.jsp", "roles[admin]");
        map.put("/list.jsp", "user");
        map.put("/**", "authc");
        return map;
    }
}
