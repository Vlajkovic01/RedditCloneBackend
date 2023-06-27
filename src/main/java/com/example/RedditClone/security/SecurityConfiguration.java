package com.example.RedditClone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(
            AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {

        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean()
            throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
        authenticationTokenFilter
                .setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.headers().cacheControl().disable();
        httpSecurity.cors();
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/posts").permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/new").permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/top").permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/hot").permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/search").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/users").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/{id}").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/users/password").permitAll()
                .antMatchers(HttpMethod.GET, "/api/communities").permitAll()
                .antMatchers(HttpMethod.GET, "/api/communities/search").permitAll()
                .antMatchers(HttpMethod.GET, "/api/communities/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/communities/{id}/posts/new").permitAll()
                .antMatchers(HttpMethod.GET, "/api/communities/{id}/posts/top").permitAll()
                .antMatchers(HttpMethod.GET, "/api/communities/{id}/posts/hot").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/communities/{id}").access("@webSecurity.amIAdminOrModerator(authentication,request,#id)")
                .antMatchers(HttpMethod.POST, "/api/communities/{id}/moderators").access("@webSecurity.amIAdminOrModerator(authentication,request,#id)")
                .antMatchers(HttpMethod.GET, "/api/communities/{id}/reports").access("@webSecurity.amIAdminOrModerator(authentication,request,#id)")
                .antMatchers(HttpMethod.POST, "/api/communities/{id}/suspend").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/communities/{idCommunity}/posts/{idPost}").access("@webSecurity.checkPostCreator(authentication,request,#idCommunity, #idPost)")
                .antMatchers(HttpMethod.DELETE, "/api/communities/{idCommunity}/posts/{idPost}").access("@webSecurity.amIAdminOrModerator(authentication,request,#idCommunity)")
                .antMatchers(HttpMethod.GET, "/api/communities/{idCommunity}/posts/{idPost}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/upload/posts/image").permitAll()
                .antMatchers(HttpMethod.POST, "/api/comments").permitAll()
                .antMatchers(HttpMethod.GET, "/api/comments/post/{id}/new").permitAll()
                .antMatchers(HttpMethod.GET, "/api/comments/post/{id}/top").permitAll()
                .antMatchers(HttpMethod.GET, "/api/comments/post/{id}/old").permitAll()
                .antMatchers(HttpMethod.POST, "/api/reports").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/reports/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/banned").permitAll()
                .antMatchers(HttpMethod.GET, "/api/banned/community/{id}/user/{username}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/banned/community/{id}/user/{username}").access("@webSecurity.amIAdminOrModerator(authentication,request,#id)")
                .antMatchers(HttpMethod.GET, "/api/banned/community/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/upload").permitAll()
                .antMatchers(HttpMethod.GET, "/api/pdf").permitAll()
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }
}
