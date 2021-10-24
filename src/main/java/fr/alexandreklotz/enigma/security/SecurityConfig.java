package fr.alexandreklotz.enigma.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Still needs to be finalized. The URLs may need to be modified, USER and ADMIN are a bit too classic.
    //Also need to configure Spring Security to use users from database
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/login").hasAnyRole()
                .antMatchers("/register").hasAnyRole()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/homepage")
                .and()
                .logout()
                .and()
                .csrf().disable();
    }

    //Needs to be finalized
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
