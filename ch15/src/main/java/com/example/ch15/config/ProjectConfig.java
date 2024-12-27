package com.example.ch15.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

    @Value("${keySetURI}")
    private String keySetUri;

    @Value("${introspectionUri}")
    private String introspectionUri;

    @Value("${resourceserver.clientID}")
    private String resourceServerClientID;

    @Value("${resourceserver.secret}")
    private String resourceServerSecret;

    private final JwtAuthenticationConverter converter;

    public ProjectConfig(JwtAuthenticationConverter converter) {
        this.converter = converter;
    }


    @Bean
    @Profile("non-opaque")
    public SecurityFilterChain nonOpaqueSecurityFilterChain(HttpSecurity http)
            throws Exception {

        http.oauth2ResourceServer(
                c -> c.jwt(
                        j -> j.jwkSetUri(keySetUri)
                                .jwtAuthenticationConverter(converter)
                )
        );


        http.authorizeHttpRequests(
                c -> c.anyRequest().authenticated()
        );

        return http.build();
    }


    @Bean
    @Profile("opaque")
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http.oauth2ResourceServer(
                c -> c.opaqueToken(
                        o -> o.introspectionUri(introspectionUri)
                                .introspectionClientCredentials(
                                        resourceServerClientID,
                                        resourceServerSecret))
        );


        http.authorizeHttpRequests(
                c -> c.anyRequest().authenticated()
        );

        return http.build();
    }


    @Bean
    @Profile("multitenant")
    public SecurityFilterChain multiTenantSecurityFilterChain(HttpSecurity http, AuthenticationManagerResolver authenticationManagerResolver)
            throws Exception {

        http.oauth2ResourceServer(
                j -> j.authenticationManagerResolver(
                        authenticationManagerResolver)
        );

        http.authorizeHttpRequests(
                c -> c.anyRequest().authenticated()
        );
        return http.build();
    }

//    @Bean
//    @Profile("multitenant")
//    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver() {
//
//        var a = new JwtIssuerAuthenticationManagerResolver(
//                "http://localhost:7070",
//                "http://localhost:8080");
//
//        return a;
//
//
//    }


    @Bean
    @Profile("multitenant")
    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver(
            JwtDecoder jwtDecoder,
            OpaqueTokenIntrospector opaqueTokenIntrospector
    ) {

        AuthenticationManager jwtAuth = new ProviderManager(new JwtAuthenticationProvider(jwtDecoder)
        );

        AuthenticationManager opaqueAuth = new ProviderManager(
                new OpaqueTokenAuthenticationProvider(opaqueTokenIntrospector)
        );

        return (request) -> {
            if ("jwt".equals(request.getHeader("type"))) {
                return jwtAuth;
            } else {
                return opaqueAuth;
            }
        };
    }

    //non-opaque
    @Bean
    @Profile("multitenant")
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withJwkSetUri("http://localhost:8080/oauth2/jwks")
                .build();
    }

    //opaque
    @Bean
    @Profile("multitenant")
    public OpaqueTokenIntrospector opaqueTokenIntrospector() {
        return new SpringOpaqueTokenIntrospector(
                "http://localhost:8080/oauth2/introspect",
                "client", "secret");
    }


}
