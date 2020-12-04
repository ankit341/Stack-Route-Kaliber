package com.kaliber.apigateway.kaliberapigateway.configuration;

import com.kaliber.apigateway.kaliberapigateway.filter.JwtFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .and().antMatcher("/**").authorizeRequests().anyRequest().authenticated()
                .and().addFilterAfter(new JwtFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests();
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/", "/kaliber-user-management/kaliberauth/**","/kaliber-user-management/kaliberauthgitlab/**","/assets/**","/*.js","/*.css","/*.eot","/*.svg","/*.woff2","/*.ttf","/*.woff","/*.jpg","/*.html","/*.scss","/bg_texture_84.png","/kaliber.ico");
    }
}