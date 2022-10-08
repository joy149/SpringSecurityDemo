package com.example.demo.ApplicationSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.demo.ApplicationSecurity.ApplicationUserPermission.COURSE_READ;
import static com.example.demo.ApplicationSecurity.ApplicationUserPermission.COURSE_WRITE;
import static com.example.demo.ApplicationSecurity.ApplicationUserRoles.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurity(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails adminUser = User.builder()
                .username("joybhowmick")
                .password(passwordEncoder.encode("password"))
 //               .roles(STUDENT.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        UserDetails studentUser = User.builder()
                .username("shaillybhowmick")
                .password(passwordEncoder.encode("password123"))
//                .roles(STUDENT.getGrantedAuthorities())
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails adminTraineeUser = User.builder()
                .username("userTrainee")
                .password(passwordEncoder.encode("password"))
//                .roles(STUDENT.getGrantedAuthorities())
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                adminUser, studentUser, adminTraineeUser
        );
    }
}
