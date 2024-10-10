package com.thomasmore.blc.labflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// dit zorgt ervoor dat we niet de default security configuration gaan gebruiken
// we gaan niet de default flow gebruiken maar wat wij hier gaan definieren
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // disable csrf (stateless applicatie heeft dit niet nodig)
        http.csrf(customizer -> customizer.disable());

        // alle requests moeten geauthenticeerd zijn
        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());

        // enabled form based login
        http.formLogin(Customizer.withDefaults());
        // http.httpBasic(Customizer.withDefaults()); // geeft ons een default login scherm

        // specifieren dat we een stateless applicatie bouwen
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        // build geeft ons het object "securityfilterchain" terug
        return http.build();
    }
}
