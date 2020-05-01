package com.example.photomanager.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 小朝
 * @date 2020/5/1
 **/
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean
     * @return 返回ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 设置SecurityManager
        factoryBean.setSecurityManager(getDefaultWebSecurityManager());
        // 设置过滤的路径 anon：不进行拦截，user：记住密码或者已经登录过
//        Map<String,String> map = new LinkedHashMap<>();
//        map.put("/login.html","anon");
//        map.put("/register.html","anon");
//        map.put("/**","user");

//        factoryBean.setFilterChainDefinitionMap(map);
        factoryBean.setLoginUrl("/login.html");
        return factoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     * @return 返回DefaultWebSecurityManager
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 管理自定义realm
        securityManager.setRealm(getRealm());
        // 管理rememberMeCookie
        securityManager.setRememberMeManager(getCookieRememberMeManager());
        return securityManager;
    }

    /**
     * 创建Realm
     * @return 返回自定义realm
     */
    @Bean
    public MyRealm getRealm(){
        MyRealm myRealm = new MyRealm();
        // 管理密码比对器
        myRealm.setCredentialsMatcher(getHashedCrendtialsMatcher());
        return myRealm;
    }

    @Bean
    public SimpleCookie getSimpleCookie(){
        SimpleCookie cookie = new SimpleCookie();
        // 设置cookie的name和时间
        cookie.setMaxAge(864000);
        cookie.setName("testRemember");
        return cookie;
    }

    @Bean
    public CookieRememberMeManager getCookieRememberMeManager(){
        // 设置记住我的cookie
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(getSimpleCookie());
        return rememberMeManager;
    }

    @Bean
    public HashedCredentialsMatcher getHashedCrendtialsMatcher(){
        // 密码比对器
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 加密方式
        matcher.setHashAlgorithmName("SHA-256");
        // 迭代次数
        matcher.setHashIterations(1024);
        return matcher;
    }
}
