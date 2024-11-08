package com.example.ch09.config;

import com.example.ch09.filter.CsrfTokenLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;


@Configuration
public class ProjectConfig {


    @Bean
    public CsrfTokenRepository customTokenRepository() {
        return new CustomCsrfTokenRepository();
    }

    @Bean
    public UserDetailsService uds() {
        var uds = new InMemoryUserDetailsManager();

        var u1 = User.withUsername("mary")
                .password("12345")
                .authorities("READ")
                .build();

        uds.createUser(u1);

        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }




    @Bean
    public SecurityFilterChain configure(HttpSecurity http)
            throws Exception {


        http.csrf(c -> {
            c.csrfTokenRepository(customTokenRepository());
            c.csrfTokenRequestHandler(
                    new CsrfTokenRequestAttributeHandler()
            );
            c.ignoringRequestMatchers("/ciao");
        });
        http.formLogin(
                c -> c.defaultSuccessUrl("/main", true)
        );


        http.addFilterAfter(
                        new CsrfTokenLogger(), CsrfFilter.class)
                .authorizeHttpRequests(
                        c -> c.anyRequest().permitAll()
                );

        return http.build();
    }
}
