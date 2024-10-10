package com.thomasmore.blc.labflow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// dit zorgt ervoor dat we niet de default security provider gaan gebruiken
// we gaan niet de default flow volgen maar deze provider "DaoAuthenticationProvider"
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // disable csrf (stateless applicatie heeft dit niet nodig)
        http.csrf(customizer -> customizer.disable());

        // alle requests moeten geauthenticeerd zijn
        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());

        // enabled form based login
        http.httpBasic(Customizer.withDefaults());

        // specifieren dat we een stateless applicatie bouwen
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        // build geeft ons het object "securityfilterchain" terug
        return http.build();
    }


    // bean voor mee te geven welke users verified zijn
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // this one is deprecated, so needs to change :)
        // Om DaoAuthenticationProvider te doen werken moeten 2 zaken gespecifieerd worden:
        // De passwordencoder voor encryptie
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        // Onze eigen userDetailsService
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}
