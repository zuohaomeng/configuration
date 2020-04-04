package com.meng.configuration.config;

import com.meng.configuration.entity.User;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author HAO.ZUO
 * @date 2020/4/4--21:11
 */
@Configuration
public class SecurityHandlerConfig {
    /**
     * 登陆成功，返回Token
     *
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new AuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                Object principal = authentication.getPrincipal();
                System.out.println(authentication.getAuthorities().toArray().toString());
                System.out.println(authentication.getPrincipal().toString());
//                Token token = tokenService.saveToken(loginUser);
//                ResponseUtil.responseJson(response, HttpStatus.OK.value());
            }
        };
    }
}
