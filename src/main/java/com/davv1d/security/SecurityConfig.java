package com.davv1d.security;

import com.davv1d.security.user.UserPrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserPrincipalDetailService userPrincipalDetailService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Autowired
    private JwtAuthTokenFilter authenticationJwtTokenFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userPrincipalDetailService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/v1/cars/availability/change").hasRole("ADMIN")
                .antMatchers("/v1/cars/availability").authenticated()
                .antMatchers("/v1/cars/brand").hasRole("ADMIN")
                .antMatchers("/v1/cars/createCars").hasRole("ADMIN")
                .antMatchers("/v1/cars/delete").hasRole("ADMIN")
                .antMatchers("/v1/cars/getCar").hasRole("ADMIN")
                .antMatchers("/v1/cars/getCars").hasRole("ADMIN")
                .antMatchers("/v1/cars/model").hasRole("ADMIN")
                .antMatchers("/v1/rental").hasRole("ADMIN")
                .antMatchers("/v1/rental/").hasRole("ADMIN")
                .antMatchers("/v1/rental/create").authenticated()
                .antMatchers("/v1/rental/user").authenticated()
                .antMatchers("/v1/loggedUser").authenticated()
                .antMatchers("/v1/users").hasRole("ADMIN")
                .antMatchers("/v1/users/email").authenticated()
                .antMatchers("/v1/api/auth/**").permitAll()
                .antMatchers("/v1/rates").permitAll()
                .antMatchers("/v1/condition/**").permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
