package com.meng.configuration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author HAO.ZUO
 * @date 2020/4/3--23:05
 */
//@Configuration
//@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 以下为配置所需保护的资源路径及权限，需要与认证服务器配置的授权部分对应
                .antMatchers("/index").hasAuthority("SystemContent")
                .antMatchers("/project/**").hasAuthority("SystemContent")
                .antMatchers("/information/**").hasAuthority("SystemContent")
                .antMatchers("/permission/**").hasAuthority("SystemContent")
                .antMatchers("/projectgroup/**").hasAuthority("SystemContent")
                .antMatchers("/history/**").hasAuthority("SystemContent");
    }

}
