package com.example.ch16.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {


    //use with ch14 auth code
    @Bean
    @Profile("custom")
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity http)
            throws Exception {

        http.oauth2Login(Customizer.withDefaults());

        http.authorizeHttpRequests(
                c -> c.anyRequest().authenticated());

        return http.build();
    }

    @Bean
    @Profile("client")
    public SecurityFilterChain clientSecurityFilterChain(HttpSecurity http)
            throws Exception {

        http.oauth2Client(Customizer.withDefaults());

        http.authorizeHttpRequests(
                c -> c.anyRequest().permitAll()
        );

        return http.build();
    }

    @Bean
    @Profile("client")
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration c1 =
                ClientRegistration.withRegistrationId("1")
                        .clientId("client")
                        .clientSecret("secret")
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .clientAuthenticationMethod(
                                ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .tokenUri("http://localhost:8080/oauth2/token")
                        .scope(OidcScopes.OPENID)
                        .build();

        var repository =
                new InMemoryClientRegistrationRepository(c1);

        return repository;
    }

    @Bean
    public OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository
    ) {

        var provider = OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();

        var cm = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                auth2AuthorizedClientRepository);

        cm.setAuthorizedClientProvider(provider);

        return cm;
    }


    //can register more than 1 oauth client
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository(
//                this.googleClientRegistration());
//    }
//
//    private ClientRegistration googleClientRegistration() {
//        return CommonOAuth2Provider.GOOGLE.getBuilder("google")
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .build();
//    }


}
