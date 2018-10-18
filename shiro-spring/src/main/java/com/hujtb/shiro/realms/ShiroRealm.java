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
 * AuthorizingRealm��AuthenticatingRealm������
 * @author hujtb
 * @create on 2018-10-10-14:58
 */

public class ShiroRealm extends AuthorizingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        // 1.��AuthenticationExceptionת����UsernamePasswordToke
        UsernamePasswordToken upToken = (UsernamePasswordToken)token;
        // 2.��upToken�л�ȡ�û���
        String username = upToken.getUsername();
        // 3.�������ݿⷽ���������ݿ��ѯ��username��Ӧ���û���¼
        System.out.println("�����ݿ��л�ȡusername" + username + "����Ӧ���û���Ϣ");
        // 4.���û������ڣ����׳�UnknownAccountException�쳣
        if("unknown".equals(username)){
            throw new UnknownAccountException("�û�������");
        }
        // 5.�����û�����������Ƿ�Ҫ�׳�������AuthenticationException�쳣
        if("monster".equals(username)){
            throw new AuthenticationException("�û�������");
        }
        // 6.�����û������������AuthenticationInfo���󲢷���
        // ������Ϣ�Ǵ����ݿ��л�ȡ
        // 1)principal:��֤��ʵ����Ϣ��������username��Ҳ���������ݱ��Ӧ���û�ʵ�������
        Object principal = username;

        // 2)credentials�������ݿ��л�ȡ������
        Object credentials = "123456";

        // 3)realmName����ǰrealm�����name�����ø����getName()�������ɡ�
        String realmName = super.getName();

        // 4)��ֵ
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
        //����md5���ܺ��ֵ
        Object result = new SimpleHash(hashAlgorithmName,credentials, salt, hashIterations);
        System.out.println(result);
    }

    /**
     * -----user��admin����ҳ��������roles����������Ҫ���������û���Ȩ���ܷ���------
     * Ч������admin�û���¼���Է���admin.jsp��user.jsp
     *      ��user�û���¼ֻ���Է���user.jsp
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1����PrincipalCollection�л�ȡδ��¼�û�����Ϣ����������û��棬ÿ�η��ʶ���ִ�����д���
        Object principal = principalCollection.getPrimaryPrincipal();

        // 2�����õ�¼���û���Ϣ����ȡ��ǰ�û��Ľ�ɫ��Ȩ��
        Set<String> roles = new HashSet<String>();
        roles.add("user");
        if("admin".equals(principal)){
            roles.add("admin");
        }

        // 3������SimpleAuthorizationInfo���󣬲�����roles����
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);

        // 4������SimpleAuthorizationInfo
        return info;
    }
}
