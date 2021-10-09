package com.kjy.myapp.springboot.config.auth;

import com.kjy.myapp.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        String enPw = passwordEncoder().encode("GUEST");
        auth.inMemoryAuthentication().withUser("GUEST").password(enPw).roles(Role.GUEST.name());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
            .csrf().disable().headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/","/login","/portfolio/**","/css/**","/img/**","/js/**","/pdf/**","h2-console/**","/guest").permitAll()
                .antMatchers("/news").hasAnyRole(Role.USER.name(),Role.GUEST.name())
                .antMatchers("/api/v1/**").hasAnyRole(Role.USER.name(), Role.GUEST.name())
                .anyRequest().authenticated()
            .and()
                .formLogin().loginPage("/login").successForwardUrl("/guest").permitAll()
            .and()
                .logout()
                    .logoutSuccessUrl("/")
            .and()
                .oauth2Login().loginPage("/login")
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }

}
