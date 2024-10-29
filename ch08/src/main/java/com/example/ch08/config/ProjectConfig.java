package com.example.ch08.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("john")
            .password("12345")
            .roles("ADMIN")
            .build();

        var user2 = User.withUsername("jane")
            .password("12345")
            .roles("MANAGER")
            .build();

        manager.createUser(user1);
        manager.createUser(user2);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {

        http.httpBasic(Customizer.withDefaults());

        //        http.authorizeHttpRequests(
        //            c -> c.requestMatchers("/hello").hasRole("ADMIN")
        //                .requestMatchers("/ciao").hasRole("MANAGER")
        //                .anyRequest().authenticated() //All other requests are accessible only
        //                by authenticated users.
        //        );

        http.authorizeHttpRequests(
            c ->
                c.requestMatchers("/hello").hasRole("ADMIN")
                    .requestMatchers("/ciao").hasRole("MANAGER")
//                    .requestMatchers(HttpMethod.GET, "/a").authenticated()
//                    .requestMatchers(HttpMethod.POST, "/a").permitAll()
//                    .anyRequest()
//                    .denyAll()
                    .requestMatchers( "/a/b/**").authenticated()
                    .requestMatchers("/product/{code:^[0-9]*$}").permitAll()
                    .requestMatchers("/email/{email:.*(?:.+@.+\\.com)}" ).permitAll()
                    .requestMatchers(".*/(us|uk|ca)+/(en|fr).*").authenticated()
                    .anyRequest()
                    .permitAll()
        );

        http.csrf(
            AbstractHttpConfigurer::disable
        );

        return http.build();
    }
}