package com.ouyang.springbootlibrarymanagement.shiro;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /**
     * 告诉shiro不要使用默认的DefaultSubject创建对象，因为不能创建Session
     * @return
     */
    @Bean
    public SubjectFactory subjectFactory(){
        return new JwtDefaultSubjectFactory();
    }

    @Bean
    public CustomRealm myShiroRealm() {
        return new CustomRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myShiroRealm());
        //关闭ShiroDao功能
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        //不需要将Shiro Session中的东西存在任何地方（包括http session中）
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);
        //禁止Subject的getSession方法
        defaultWebSecurityManager.setSubjectFactory(subjectFactory());
        return defaultWebSecurityManager;
    }



    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager());
        shiroFilter.setLoginUrl("/unauthenticated");
        shiroFilter.setUnauthorizedUrl("/unauthorized");
        /*
         * c. 添加jwt过滤器，并在下面注册
         * 也就是将jwtFilter注册到shiro的Filter中
         * 指定除了login和logout之外的请求都先经过jwtFilter
         * */
        Map<String, Filter> filterMap = new HashMap<>();
        //这个地方其实另外两个filter可以不设置，默认就是
        filterMap.put("anon", new AnonymousFilter());
        filterMap.put("jwt", new JwtFilter());
        filterMap.put("logout", new LogoutFilter());
        shiroFilter.setFilters(filterMap);

        // 拦截器
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        filterRuleMap.put("/login", "anon");
        filterRuleMap.put("/logout", "logout");
        filterRuleMap.put("/**", "jwt");
        shiroFilter.setFilterChainDefinitionMap(filterRuleMap);

        return shiroFilter;
    }
    //shiro和Spring AOP整合时的特殊设置
//    @Bean
//    @ConditionalOnMissingBean
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
//        defaultAAP.setProxyTargetClass(true);
//        return defaultAAP;
//    }
}
