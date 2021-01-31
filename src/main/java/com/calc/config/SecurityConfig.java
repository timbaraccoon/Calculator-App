package com.calc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder().encode("test123")).roles("USER")
                .and()
                .withUser("user2").password(passwordEncoder().encode("test123")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("test123")).roles("ADMIN");


//        User.UserBuilder users = User.withDefaultPasswordEncoder();
//
//        auth.inMemoryAuthentication()
//                .withUser(users.username("mary").password("test123").roles("USER"))
//                .withUser(users.username("john").password("test123").roles("USER"))
//                .withUser(users.username("boris").password("test123").roles("ADMIN"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/api/calculate").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/**").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .and().authorizeRequests()
                .and().csrf().ignoringAntMatchers("/h2-console/**")
                .and().headers().frameOptions().sameOrigin()
                .and()
                .formLogin()
                .loginPage("/showLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
//
    }

}
