package com.example.ch14.config;


import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

@Configuration
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain asFilterChain(HttpSecurity http)
            throws Exception {


        OAuth2AuthorizationServerConfiguration
                .applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        http.exceptionHandling((e) ->
                e.authenticationEntryPoint(
                        new LoginUrlAuthenticationEntryPoint("/login"))
        );

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {

        http.formLogin(Customizer.withDefaults());

        http.authorizeHttpRequests(
                c -> c.anyRequest().authenticated()
        );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withUsername("bill")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();

    }


    /*
     * code_challenge=QYPAZ5NU8yvtlQ…—If using the authorization code enhanced with PKCE
     * (discussed in chapter 13), you must provide the code challenge with the authorization request.
     * When requesting the token, the client must send the verifier pair to prove they are the same application
     * that initially sent this request. The PKCE flow is enabled by default.
     * */

    /*
     *
     * Because we enabled the OpenID Connect protocol,
     * so we don’t only rely on OAuth 2, an ID token is also present in the token response.
     * If the client had been registered using the refresh token grant type,
     *
     *  a refresh token would have also been generated and sent through the response.
     * */
    @Bean
    @Profile("authorization-code")
    public RegisteredClientRepository authCodeRegisteredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("client")
                .clientSecret("secret")
                .clientAuthenticationMethod(
                        ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(
                        AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("https://www.manning.com/authorized")
                .tokenSettings(
                        TokenSettings.builder()
                                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                                .accessTokenTimeToLive(Duration.ofHours(24))
                                .build()
                )
                .scope(OidcScopes.OPENID)
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }


    /*
     * none-opaque tokens - store data JWT
     * */

    /*
     * As an opaque token doesn’t contain data,
     * how can someone validate it and get more details about the client (and potentially the user)
     * for whom the authorization server generated it?
     * The easiest (and most used) way is to directly ask the authorization server.
     * The authorization server exposes an endpoint where one can send a request with the token.
     * The authorization server replies with the needed details about the token.
     * This process is called introspection (figure 14.8).
     * */
    @Bean
    @Profile("client-credentials")
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient =
                RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("client")
                        .clientSecret("secret")
                        .clientAuthenticationMethod(
                                ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .tokenSettings(TokenSettings.builder()
                                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)        //access token format defines opaqueness
                                .accessTokenTimeToLive(Duration.ofHours(12))
                                .build())
                        .scope("CUSTOM")
                        .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource()
            throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey publicKey =
                (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey =
                (RSAPrivateKey) keyPair.getPrivate();

        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {

        return AuthorizationServerSettings.builder()
                .build();
    }

    /*
     * But first things first!
     * We need to add a custom value in the access token’s body to the SecurityConfig class.
     * In the authorization server, you do this by adding a bean of type OAuth2TokenCustomizer.
     * The next code snippet demonstrates such a bean’s definition.
     * To simplify things and allow you to focus on the example,
     * I added a dummy value in a field I named "priority". In real-world apps, such custom fields would have a purpose,
     * and you’d potentially have to write certain logic for setting their value:
     * */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            JwtClaimsSet.Builder claims = context.getClaims();
            claims.claim("priority", "HIGH");
        };
    }


    @Bean
    public RegisteredClientRepository registeredClientRepositoryComplete() {
        RegisteredClient registeredClient =
        RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("client")
                .clientSecret("secret")
                .clientAuthenticationMethod(
                        ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                        .accessTokenTimeToLive(Duration.ofHours(12))
                        .build())
                .scope("CUSTOM")
                .build();

        RegisteredClient resourceServer =
        RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("resource_server")
                .clientSecret("resource_server_secret")
                .clientAuthenticationMethod(
                        ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(
                        AuthorizationGrantType.CLIENT_CREDENTIALS)
                .build();

        return new InMemoryRegisteredClientRepository(
                registeredClient,
                resourceServer);
    }
}
