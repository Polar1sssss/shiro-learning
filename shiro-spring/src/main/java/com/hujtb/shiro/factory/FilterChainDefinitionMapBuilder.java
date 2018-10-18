package com.hujtb.shiro.factory;

import java.util.LinkedHashMap;

/**
 * ���ù����࣬ʵ������LinkedMap���������Ÿ���ҳ��ķ���Ȩ��
 * @author hujtb
 * @create on 2018-10-17-18:45
 */
public class FilterChainDefinitionMapBuilder {

    public LinkedHashMap<String, String> buildFilterChainDefination(){
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        //Ҫ����˳��д
        map.put("/login.jsp", "anon");
        map.put("/shiro/logout.jsp", "logout");
        map.put("/user.jsp", "roles[user]");
        map.put("/admin.jsp", "roles[admin]");
        map.put("/list.jsp", "user");
        map.put("/**", "authc");
        return map;
    }
}
