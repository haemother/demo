package com.security.mybatis.plus.securitymybatisplus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public  BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.formLogin()
                .loginPage("/security/login")
                .loginProcessingUrl("/login")
//                .successForwardUrl("/security/index")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                                        HttpServletResponse httpServletResponse,
                                                        Authentication authentication)
                            throws IOException, ServletException {
//                        httpServletResponse.sendRedirect("https://www.baidu.com/");
                        httpServletResponse.sendRedirect("/security/index");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                                        HttpServletResponse httpServletResponse, AuthenticationException e)
                            throws IOException, ServletException {
                        httpServletResponse.sendRedirect("/security/fail");
                    }
                })
                .usernameParameter("un")
                .passwordParameter("pwd");

        http.authorizeRequests()
                .antMatchers("/security/login","/security/fail").permitAll()
//                .antMatchers("/security/login").permitAll()
                .anyRequest().authenticated();
        http.csrf().disable();
    }
}
