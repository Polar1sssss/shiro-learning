package com.hujtb;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Simple Quickstart application showing how to use Shiro's API.
 *
 * @since 0.9 RC2
 */
public class HelloWorld {

    private static final transient Logger log = LoggerFactory.getLogger(HelloWorld.class);


    public static void main(String[] args) {

        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        // get the currently executing user:
        // ��ȡ��ǰ��subject
        Subject currentUser = SecurityUtils.getSubject();

        // Do some stuff with a Session (no need for a web or EJB container!!!)
        // ����ʹ��session
        // ��ȡsession
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("---->Retrieved the correct value! [" + value + "]");
        }

        // let's login the current user so we can check against roles and permissions:
        // ���Ե�ǰ�û��Ƿ��Ѿ�����֤�����Ƿ��Ѿ���¼������subject��isAuthenticated����
        if (!currentUser.isAuthenticated()) {
            // ���û����������װΪUsernamePasswordToken����
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            token.setRememberMe(true);
            try {
                // ִ�е�¼token
                currentUser.login(token);
                // δ֪�˻��쳣
            } catch (UnknownAccountException uae) {
                log.info("There is no user with username of " + token.getPrincipal());
                // ���벻ƥ���쳣
            } catch (IncorrectCredentialsException ice) {
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
                //  �û��������쳣
            } catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ������֤ʱ�쳣�ĸ���
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }

        //say who they are:
        //print their identifying principal (in this case, a username):
        log.info("---->User [" + currentUser.getPrincipal() + "] logged in successfully.");

        // �û���ɫ����
        if (currentUser.hasRole("Schwartz")) {
            log.info("---->May the Schwartz be with you!");
        } else {
            log.info("---->Hello, mere mortal.");
        }

        //test a typed permission (not instance-level)
        // �����û��Ƿ����ĳ����Ϊ
        if (currentUser.isPermitted("lightsaber:wield")) {
            log.info("You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        //a (very powerful) Instance Level permission:
        if (currentUser.isPermitted("user:delete:zhangsan")) {
            log.info("----> You are permitted to 'delete' the user with license plate (id) 'zhangsan'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        //all done - log out!
        System.out.println(currentUser.isAuthenticated());
        currentUser.logout();
        System.out.println(currentUser.isAuthenticated());
        System.exit(0);
    }
}
