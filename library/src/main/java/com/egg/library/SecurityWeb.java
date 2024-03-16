/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.library;

import com.egg.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author caro9
 */

@Configuration
public class SecurityWeb {
    
    @Autowired
    public UserService userService;
    
    @Autowired 
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    } 

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/authors/create/**").hasRole("ADMIN")
                .requestMatchers("/authors/edit/**").hasRole("ADMIN")
                .requestMatchers("/publishers/create/**").hasRole("ADMIN")
                .requestMatchers("/publishers/edit/**").hasRole("ADMIN")
                .requestMatchers("/books/create/**").hasRole("ADMIN")
                .requestMatchers("/books/edit/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasAnyRole("ADMIN" , "USER")
                .anyRequest().permitAll()
            )
            .httpBasic(withDefaults())
            .formLogin(customizer -> customizer
                .loginPage("/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
            )
             
            .logout(logout -> 
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedPage("/accessDenied")
                .authenticationEntryPoint((request, response, authException) -> 
                        response.sendRedirect("/error")));



        return http.build();
    }



}
