package config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableAsync
public class ProjectConfig {

    private final AuthenticationProvider authenticationProvider;
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    public ProjectConfig(
        final AuthenticationProvider authenticationProvider,
        final CustomAuthenticationSuccessHandler authenticationSuccessHandler,
        final CustomAuthenticationFailureHandler authenticationFailureHandler
    ) {
        this.authenticationProvider = authenticationProvider;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    //Sub threads will inherit userdetails from parent threads
    @Bean
    public InitializingBean initializingBean() {
        return () -> SecurityContextHolder.setStrategyName(
            SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Bean
    public UserDetailsService uds() {
        var uds = new InMemoryUserDetailsManager();

        uds.createUser(
            User.withDefaultPasswordEncoder()
                .username("john")
                .password("12345")
                .authorities("read")
                .build()
        );

        uds.createUser(
            User.withDefaultPasswordEncoder()
                .username("bill")
                .password("12345")
                .authorities("write")
                .build()
        );


        return uds;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {

        http.httpBasic(c -> {
            c.realmName("OTHER");
            c.authenticationEntryPoint(new CustomEntryPoint());
        });


        http.formLogin(c ->
            c.successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
        );
        http.authenticationProvider(authenticationProvider);

        http.authorizeHttpRequests(c -> c.anyRequest().authenticated());

        return http.build();
    }
}
