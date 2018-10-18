package com.hujtb.shiro.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * AuthorizingRealm是AuthenticatingRealm的子类
 * @author hujtb
 * @create on 2018-10-10-14:58
 */

public class ShiroRealm extends AuthorizingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        // 1.把AuthenticationException转换成UsernamePasswordToke
        UsernamePasswordToken upToken = (UsernamePasswordToken)token;
        // 2.从upToken中获取用户名
        String username = upToken.getUsername();
        // 3.调用数据库方法，从数据库查询出username对应的用户记录
        System.out.println("从数据库中获取username" + username + "所对应的用户信息");
        // 4.若用户不存在，则抛出UnknownAccountException异常
        if("unknown".equals(username)){
            throw new UnknownAccountException("用户不存在");
        }
        // 5.根据用户情况，决定是否要抛出其他的AuthenticationException异常
        if("monster".equals(username)){
            throw new AuthenticationException("用户被锁定");
        }
        // 6.根据用户情况，来构建AuthenticationInfo对象并返回
        // 以下信息是从数据库中获取
        // 1)principal:认证的实体信息，可以是username，也可以是数据表对应的用户实体类对象
        Object principal = username;

        // 2)credentials：从数据库中获取的密码
        Object credentials = "123456";

        // 3)realmName：当前realm对象的name，调用父类的getName()方法即可。
        String realmName = super.getName();

        // 4)盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);

        SimpleAuthenticationInfo info = null;
        info =  new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);

        return info;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        Object credentials = "123456";
        Object salt = ByteSource.Util.bytes("admin");
        int hashIterations = 1024;
        //计算md5加密后的值
        Object result = new SimpleHash(hashAlgorithmName,credentials, salt, hashIterations);
        System.out.println(result);
    }

    /**
     * -----user和admin两个页面配置了roles拦截器，需要对这两个用户授权才能访问------
     * 效果：用admin用户登录可以访问admin.jsp和user.jsp
     *      用user用户登录只可以访问user.jsp
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1、从PrincipalCollection中获取未登录用户的信息，如果不配置缓存，每次访问都会执行这行代码
        Object principal = principalCollection.getPrimaryPrincipal();

        // 2、利用登录的用户信息来获取当前用户的角色或权限
        Set<String> roles = new HashSet<String>();
        roles.add("user");
        if("admin".equals(principal)){
            roles.add("admin");
        }

        // 3、创建SimpleAuthorizationInfo对象，并设置roles属性
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);

        // 4、返回SimpleAuthorizationInfo
        return info;
    }
}
