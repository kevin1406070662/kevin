package com.kevin.framework.shiro.autoconfigure;


import com.kevin.framework.redis.ICacheService;
import com.kevin.framework.shiro.TokenService;
import com.kevin.framework.shiro.config.JwtProperties;
import com.kevin.framework.shiro.jwt.filter.JWTFilter;
import com.kevin.framework.shiro.realm.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(JwtProperties.class)
public class ShiroConfiguration {
    private static final String JWT_FILTER_NAME = "jwt";

    @Bean
    public CarWashModularRealmAuthenticator userModularRealmAuthenticator() {
        //自己重写的ModularRealmAuthenticator
        CarWashModularRealmAuthenticator modularRealmAuthenticator = new CarWashModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    /**
     * 密码登录时使用该匹配器进行匹配
     *
     * @return HashedCredentialsMatcher
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 设置哈希算法名称
        matcher.setHashAlgorithmName("MD5");
        // 设置哈希迭代次数
        matcher.setHashIterations(3);
        // 设置存储凭证十六进制编码
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }


    /**
     * 密码登录Realm
     *
     * @param matcher 密码匹配器
     * @return PasswordRealm
     */
    @Bean
    public SysPasswordRealm sysPasswordRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        SysPasswordRealm userRealm = new SysPasswordRealm();
        userRealm.setCredentialsMatcher(matcher);
        userRealm.setAuthorizationCachingEnabled(true);
        userRealm.setCachingEnabled(true);
        return userRealm;
    }

    /**
     * 商家端登录Realm
     *
     * @param matcher 密码匹配器
     * @return MerchantRealm
     */
    @Bean
    public MerchantRealm codeRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        MerchantRealm codeRealm = new MerchantRealm();
        // 是否使用缓存权限
        codeRealm.setCredentialsMatcher(matcher);
        codeRealm.setAuthorizationCachingEnabled(true);
        codeRealm.setCachingEnabled(true);
        // codeRealm.setAuthorizationCachingEnabled(true);
        return codeRealm;
    }

    /**
     * 自定义realm，实现登录授权流程
     */
    @Bean("TokentCheckCMRealm")
    public TokentCheckCMRealm shiroUserRealm() {
        TokentCheckCMRealm userRealm = new TokentCheckCMRealm();
        userRealm.setCachingEnabled(true);
        userRealm.setAuthorizationCachingEnabled(true);
        // userRealm.setAuthenticationCachingEnabled(true);
        return userRealm;
    }



    /**
     * 生成一个ShiroRedisCacheManager 这没啥好说的
     * 可用于缓存用户信息到redis 中
     *
     * @return
     * @author Super小靖
     * @date 2018/8/29
     **/
//    private ShiroRedisCacheManager cacheManager(ICacheService iCacheService, JwtProperties jwtProperties) {
//        return new ShiroRedisCacheManager(iCacheService, jwtProperties);
//    }
    @Bean("securityManager")
    @DependsOn({"TokentCheckCMRealm"})
    public DefaultWebSecurityManager securityManager(TokentCheckCMRealm shiroUserRealm,
                                                     @Qualifier("sysPasswordRealm") SysPasswordRealm passwordRealm,
                                                     @Qualifier("codeRealm") MerchantRealm codeRealm,                                          @Qualifier("userModularRealmAuthenticator")
                                                      CarWashModularRealmAuthenticator userModularRealmAuthenticator
    ) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm
        securityManager.setAuthenticator(userModularRealmAuthenticator);
        List<Realm> realms = new ArrayList<>();
        // 添加多个realm
        realms.add(passwordRealm);
        realms.add(codeRealm);
        realms.add(shiroUserRealm);
        CarWashModularRealmAuthorizer authorizer = new CarWashModularRealmAuthorizer();
        // 这个一定要写在  securityManager.setRealms(realms); 先初始化 CarWashModularRealmAuthorizer
        securityManager.setAuthorizer(authorizer);
        securityManager.setRealms(realms);
        // 关闭自带session
        DefaultSessionStorageEvaluator evaluator = new DefaultSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(false);
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(evaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    @Bean
    public FilterRegistrationBean delegatingFilterProxy() {
        FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    public JWTFilter jwtFilterBean(TokenService tokenService) {
        return new JWTFilter(tokenService);
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager,
                                                         TokenService tokenService) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置 SecurityManager
        bean.setSecurityManager(securityManager);
        // 设置未登录跳转url
        // bean.setLoginUrl("/user/unLogin");
        //设置未授权提示页面
        //bean.setUnauthorizedUrl("/noAuth");
        Map<String, Filter> filter = new LinkedHashMap<>(1);
        filter.put("jwt", new JWTFilter());
        bean.setFilters(filter);
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/", "anon");
        filterMap.put("/api/test/**", "anon");//登陆放行界面放行
        filterMap.put("/tool/gen/**","anon");
        //filterMap.put("/system/sysMenu/**","anon");
        filterMap.put("/swagger-resources", "anon");
        filterMap.put("/swagger-resources/configuration/ui", "anon");
        filterMap.put("/doc.html", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/v2/api-docs-ext", "anon");
        filterMap.put("/webjars/**", "anon");   //http://127.0.0.1
        filterMap.put("/service-worker.js", "anon");
        filterMap.put("/**", "jwt");
        bean.setFilterChainDefinitionMap(filterMap);
        return bean;
    }



    /**
     * 开启注解
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 管理生命周期
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 注解访问授权动态拦截，不然不会执行doGetAuthenticationInfo
     *
     * @param securityManager 安全管理器
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
