package cn.high.mx.framework.security.config;

import cn.high.mx.framework.security.core.jwt.JwtDefaultSubjectFactory;
import cn.high.mx.framework.security.core.ShiroFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
@ConditionalOnBean({JwtAutoConfiguration.class})
public class ShiroAutoConfiguration {

    private final JwtAutoConfiguration jwtAutoConfiguration;

    @Bean
    public SubjectFactory subjectFactory() {
        return new JwtDefaultSubjectFactory();
    }

    @Bean
    public Realm realm() {
        return jwtAutoConfiguration.getJwtRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        /*
         * b
         * */
        // 关闭 ShiroDAO 功能
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        // 不需要将 Shiro Session 中的东西存到任何地方（包括 Http Session 中）
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        //禁止Subject的getSession方法
        securityManager.setSubjectFactory(subjectFactory());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilter shiroRouting = jwtAutoConfiguration.getShiroRouting();
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager());
        Map<String, Filter> filterMap = shiroRouting.getFilterMap();
        //这个地方其实另外两个filter可以不设置，默认就是
        shiroFilter.setFilters(filterMap);
        // 拦截器  注意，这里要使用绝对路径，
        Map<String, String> filterRuleMap = shiroRouting.getFilterRuleMap();
        shiroFilter.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilter;
    }
}
