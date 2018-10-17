package com.hujtb.shiro.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 * @author hujtb
 * @create on 2018-10-10-14:58
 */
public class SecondRealm extends AuthenticatingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("[SecondRealm] doGetAuthenticationInfo");
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
}
